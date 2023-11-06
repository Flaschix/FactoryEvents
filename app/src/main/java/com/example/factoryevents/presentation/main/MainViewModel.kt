package com.example.factoryevents.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.factoryevents.domain.entity.AuthState
import com.example.factoryevents.domain.entity.User
import com.example.factoryevents.domain.usecase.CheckAuthStateUseCase
import com.example.factoryevents.domain.usecase.GetAuthStateFlowUseCase
import com.example.factoryevents.domain.usecase.GetUserUseCase
import com.example.factoryevents.presentation.HSE.HseScreenState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getAuthStateFlowUseCase: GetAuthStateFlowUseCase,
    private val checkAuthStateUseCase: CheckAuthStateUseCase,
): ViewModel(){

    val authState: Flow<AuthState> = getAuthStateFlowUseCase()

    fun performAuthResult(){
        viewModelScope.launch {
            checkAuthStateUseCase()
        }
    }
}