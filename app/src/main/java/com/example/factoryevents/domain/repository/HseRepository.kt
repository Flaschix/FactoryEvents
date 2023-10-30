package com.example.factoryevents.domain.repository

import com.example.factoryevents.domain.entity.AuthState
import com.example.factoryevents.domain.entity.HSE
import com.example.factoryevents.domain.entity.OJT
import kotlinx.coroutines.flow.StateFlow

interface HseRepository {

    fun getAuthStateFlow(): StateFlow<AuthState>

    fun getHSEList(): StateFlow<List<HSE>>

    fun getOJTList(): StateFlow<List<OJT>>

    suspend fun checkAuthState()
}