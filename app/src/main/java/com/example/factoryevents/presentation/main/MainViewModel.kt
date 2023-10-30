package com.example.factoryevents.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.factoryevents.domain.entity.AuthState
import com.example.factoryevents.domain.usecase.CheckAuthStateUseCase
import com.example.factoryevents.domain.usecase.GetAuthStateFlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getAuthStateFlowUseCase: GetAuthStateFlowUseCase,
    private val checkAuthStateUseCase: CheckAuthStateUseCase
): ViewModel(){

    val authState: Flow<AuthState> = getAuthStateFlowUseCase()


    fun performAuthResult(){
        viewModelScope.launch {
            checkAuthStateUseCase()
        }
    }
}