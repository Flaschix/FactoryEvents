package com.example.factoryevents.data.reposiroty

import android.app.Application
import android.content.Context
import com.example.factoryevents.data.mapper.DataMapper
import com.example.factoryevents.domain.entity.AccessType
import com.example.factoryevents.domain.entity.AuthState
import com.example.factoryevents.domain.entity.HSE
import com.example.factoryevents.domain.entity.OJT
import com.example.factoryevents.domain.entity.Order
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


    //authState//////////////////

    private val authStateFlowEvents = MutableSharedFlow<Unit>(replay = 1)

    private val authStateFlow = flow {
        authStateFlowEvents.emit(Unit)

        authStateFlowEvents.collect{
            val authState = if(GoogleSignIn.getLastSignedInAccount(context) != null) AuthState.Authorized else AuthState.NotAuthorized

            emit(authState)

//            userFlowEvent.emit(Unit)
        }
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = AuthState.Initial
    )


    override fun getAuthStateFlow(): StateFlow<AuthState> = authStateFlow

    override suspend fun checkAuthState() {
        authStateFlowEvents.emit(Unit)
    }

    //workerHSE/////////////////

    private val _workerHSEList = mutableListOf<WorkerHSE>()

    private val workerHSEList: List<WorkerHSE>
        get() = _workerHSEList.toList()

    private val hseWorkerDataEvent = MutableSharedFlow<Unit>(replay = 1)
    private val refreshedListFlow = MutableSharedFlow<List<WorkerHSE>>()

    private val loadedWorkerHseListFlow = flow {

        hseWorkerDataEvent.emit(Unit)

        hseWorkerDataEvent.collect{
            if(workerHSEList.isNotEmpty()) emit(workerHSEList)

            val list = mapper.mapResponseToHSE()

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


    //OJT ////////////////////////////////

    private val _ojtList = mutableListOf<OJT>()

    private val ojtList: List<OJT>
        get() = _ojtList.toList()

    private val ojtDataEvent = MutableSharedFlow<Unit>(replay = 1)
    private val refreshedOJTListFlow = MutableSharedFlow<List<OJT>>()

    private val loadedOJTListFlow = flow {

        ojtDataEvent.emit(Unit)

        ojtDataEvent.collect{
            if(ojtList.isNotEmpty()) emit(ojtList)

            val list = mapper.mapResponseToOJT()

            _ojtList.addAll(list)

            emit(ojtList)
        }
    }

    private val OJTes: StateFlow<List<OJT>> = loadedOJTListFlow
        .mergeWith(refreshedOJTListFlow)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.Lazily,
            initialValue = ojtList
        )

    override fun getOJTList(): StateFlow<List<OJT>> = OJTes


    //User///////////////////////////////////////

    private var _user = User()

    private val user: User
        get() = _user

//    private val userFlowEvent = MutableSharedFlow<Unit>(replay = 1)
//
//    private val userFlow = flow {
//        userFlowEvent.emit(Unit)
//
//        userFlowEvent.collect {
//            if (user.rank != AccessType.NONE) {
//                emit(user)
//                return@collect
//            }
//
//            val mapUser = mapper.mapResponseToUser()
//            _user = mapUser
//            emit(user)
//
//        }
//    }.retry(2) {
//        delay(RETRY_TIME_OUT)
//        true
//    }
//
//    private val getUserExist: StateFlow<User> = userFlow
//        .stateIn(
//            scope = coroutineScope,
//            started = SharingStarted.Lazily,
//            initialValue = user
//        )

    override fun getUser(): StateFlow<User> = flow{
        if (user.rank != AccessType.NONE) {
            emit(user)
            return@flow
        }

        val mapUser = mapper.mapResponseToUser()
        _user = mapUser
        emit(user)
    }.retry {
        delay(RETRY_TIME_OUT)
        true
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = User()
    )

    override suspend fun createOrder(order: Order) {
        TODO("Not yet implemented")
    }


    private companion object{
        const val RETRY_TIME_OUT: Long = 5000
    }
}