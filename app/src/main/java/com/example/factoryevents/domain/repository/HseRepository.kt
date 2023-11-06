package com.example.factoryevents.domain.repository

import com.example.factoryevents.domain.entity.AuthState
import com.example.factoryevents.domain.entity.HSE
import com.example.factoryevents.domain.entity.OJT
import com.example.factoryevents.domain.entity.User
import com.example.factoryevents.domain.entity.WorkerHSE
import kotlinx.coroutines.flow.StateFlow

interface HseRepository {

    fun getAuthStateFlow(): StateFlow<AuthState>

    fun getWorkerHSEList(): StateFlow<List<WorkerHSE>>

    fun getOJTList(): StateFlow<List<OJT>>

    suspend fun checkAuthState()

    fun getUser(): StateFlow<User>
}