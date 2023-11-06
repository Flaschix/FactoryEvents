package com.example.factoryevents.data.reposiroty

import android.app.Application
import android.content.Context
import com.example.factoryevents.data.mapper.DataMapper
import com.example.factoryevents.domain.entity.AccessType
import com.example.factoryevents.domain.entity.AuthState
import com.example.factoryevents.domain.entity.HSE
import com.example.factoryevents.domain.entity.OJT
import com.example.factoryevents.domain.entity.User
import com.example.factoryevents.domain.entity.WorkerHSE
import com.example.factoryevents.domain.repository.HseRepository
import com.example.factoryevents.extensions.mergeWith
import com.example.factoryevents.presentation.main.LoginState
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class HseRepositoryImpl @Inject constructor(
    private val mapper: DataMapper,
    private val context: Context
): HseRepository {

    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    private val authStateFlowEvents = MutableSharedFlow<Unit>(replay = 1)

    private val authStateFlow = flow {
        authStateFlowEvents.emit(Unit)

        authStateFlowEvents.collect{
            val authState = if(GoogleSignIn.getLastSignedInAccount(context) != null) AuthState.Authorized else AuthState.NotAuthorized

            emit(authState)

            userFlowEvent.emit(Unit)
        }
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = AuthState.Initial
    )


    override fun getAuthStateFlow(): StateFlow<AuthState> = authStateFlow


    private val _workerHSEList = mutableListOf<WorkerHSE>()

    private val workerHSEList: List<WorkerHSE>
        get() = _workerHSEList.toList()

    private val hseWorkerDataEvent = MutableSharedFlow<Unit>(replay = 1)
    private val refreshedListFlow = MutableSharedFlow<List<WorkerHSE>>()

    private val loadedWorkerHseListFlow = flow {

        hseWorkerDataEvent.emit(Unit)

        hseWorkerDataEvent.collect{
            if(workerHSEList.isNotEmpty()) emit(workerHSEList)

            val list: List<WorkerHSE> = arrayListOf(
                WorkerHSE(
                    3,
                    "Kol",
                    "SDF",
                    arrayListOf(HSE("Covid","2","3","5","5.02")
                    )
                )
            )

            _workerHSEList.addAll(list)

            emit(workerHSEList)
        }
    }

    private val workerHSEs: StateFlow<List<WorkerHSE>> = loadedWorkerHseListFlow
        .mergeWith(refreshedListFlow)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.Lazily,
            initialValue = workerHSEList
        )


    override fun getWorkerHSEList(): StateFlow<List<WorkerHSE>> = workerHSEs
    override fun getOJTList(): StateFlow<List<OJT>> {
        TODO("Not yet implemented")
    }

    override suspend fun checkAuthState() {
        authStateFlowEvents.emit(Unit)
    }

    private var _user = User()

    private val user: User
        get() = _user

    private val userFlowEvent = MutableSharedFlow<Unit>(replay = 1)

    private val userFlow = flow {
        userFlowEvent.emit(Unit)

        userFlowEvent.collect {
            if (user.rank != AccessType.NONE) {
                emit(user)
                return@collect
            }

            val mapUser = mapper.mapResponseToUser()
            _user = mapUser
            emit(user)

        }
    }.retry(2) {
        delay(RETRY_TIME_OUT)
        true
    }

    private val getUserExist: StateFlow<User> = userFlow
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.Lazily,
            initialValue = user
        )



    override fun getUser(): StateFlow<User> = getUserExist
//        flow{
//        delay(5000)
//        emit(mapper.mapResponseToUser())
//
//    }.retry {
//        delay(RETRY_TIME_OUT)
//        true
//    }.stateIn(
//        scope = coroutineScope,
//        started = SharingStarted.Lazily,
//        initialValue = User()
//    )



    private companion object{
        const val RETRY_TIME_OUT: Long = 5000
    }
}