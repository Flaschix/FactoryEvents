package com.example.factoryevents.data.reposiroty

import android.app.Application
import android.content.Context
import com.example.factoryevents.data.mapper.DataMapper
import com.example.factoryevents.domain.entity.AuthState
import com.example.factoryevents.domain.entity.HSE
import com.example.factoryevents.domain.entity.OJT
import com.example.factoryevents.domain.repository.HseRepository
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
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
        }
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = AuthState.Initial
    )


    override fun getAuthStateFlow(): StateFlow<AuthState> = authStateFlow

    override fun getHSEList(): StateFlow<List<HSE>> {
        TODO("Not yet implemented")
    }

    override fun getOJTList(): StateFlow<List<OJT>> {
        TODO("Not yet implemented")
    }

    override suspend fun checkAuthState() {
        authStateFlowEvents.emit(Unit)
    }
}