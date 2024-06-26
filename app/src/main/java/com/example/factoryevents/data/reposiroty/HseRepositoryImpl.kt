package com.example.factoryevents.data.reposiroty

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.util.Log
import com.example.factoryevents.data.mapper.DataMapper
import com.example.factoryevents.domain.entity.AccessType
import com.example.factoryevents.domain.entity.AuthState
import com.example.factoryevents.domain.entity.OJT
import com.example.factoryevents.domain.entity.Order
import com.example.factoryevents.domain.entity.User
import com.example.factoryevents.domain.entity.WorkerHSE
import com.example.factoryevents.domain.repository.HseRepository
import com.example.factoryevents.extensions.mergeWith
import com.google.android.gms.auth.api.signin.GoogleSignIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn
import java.io.ByteArrayOutputStream
import java.io.IOException
import javax.inject.Inject
import kotlin.math.log


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

    private var _currentWeek: Int = 16

    private val currentWeek: Int
        get() = _currentWeek

    private val hseWorkerDataEvent = MutableSharedFlow<Int>(replay = 1)
    private val refreshedListFlow = MutableSharedFlow<List<WorkerHSE>>()

    private val loadedWorkerHseListFlow = flow {

        hseWorkerDataEvent.emit(currentWeek)
        Log.d("TEST_HSE_LOAD", "currentWeek: $currentWeek")

        hseWorkerDataEvent.collect{
//            Log.d("REFRESH_TEST", "check list: $workerHSEList")

            if(it != 0 && workerHSEList.isNotEmpty()) {
                emit(workerHSEList)
                return@collect
            }

            Log.d("TEST_HSE_LOAD", "it: $it")
            val list = mapper.mapResponseToHSE(it)

            _workerHSEList.addAll(list)

//            Log.d("REFRESH_TEST", "check list after: $workerHSEList")

            emit(workerHSEList)
        }
    }.retry(2) {
        delay(RETRY_TIME_OUT)
        true
    }

    private val workerHSEs: StateFlow<List<WorkerHSE>> = loadedWorkerHseListFlow
        .mergeWith(refreshedListFlow)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.Lazily,
            initialValue = workerHSEList
        )

    override suspend fun refreshHSEList(week: Int) {
        _workerHSEList.clear()
        Log.d("TEST_HSE_LOAD", "refresh: 17")
        hseWorkerDataEvent.emit(week)
    }


    override fun getWorkerHSEList(): StateFlow<List<WorkerHSE>> = workerHSEs
//    override fun getWorkerHSEList(week: Int): StateFlow<List<WorkerHSE>> = flow {
//        hseWorkerDataEvent.emit(Unit)
//
//        hseWorkerDataEvent.collect{
//            if(currentWeek == week) {
//                emit(workerHSEList)
//                return@collect
//            }
//
//            if(week != 0) _currentWeek = week
//
//            val list = mapper.mapResponseToHSE(currentWeek)
//
//            _workerHSEList.addAll(list)
//
//            emit(workerHSEList)
//        }
//    }.retry {
//        delay(RETRY_TIME_OUT)
//        true
//    }.stateIn(
//        scope = coroutineScope,
//        started = SharingStarted.Lazily,
//        initialValue = workerHSEList
//    )


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

            try {
//                Log.d("MYTEST", user.toString())
                val list = mapper.mapResponseToOJT(user)
                _ojtList.addAll(list)
                emit(ojtList)
            } catch (e: Exception) {

            }
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

    override fun getUser(): StateFlow<User> = flow{
        if (user.rank != AccessType.NONE) {
            emit(user)
            return@flow
        }

        GoogleSignIn.getLastSignedInAccount(context)?.let {
            try {
                val newUser = mapper.mapResponseToUser(it.displayName!!)
//                val newUser = User(mail = "dwad@fesfs.com",rank = AccessType.L3)
                _user = newUser
                emit(user)
            } catch (e: Exception) {

            }
        }

    }.retry {
        delay(RETRY_TIME_OUT)
        true
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = User()
    )

    override suspend fun createOrder(order: Order) {
        mapper.mapResponseToReport(order)
    }



    private companion object{
        const val RETRY_TIME_OUT: Long = 5000
    }
}