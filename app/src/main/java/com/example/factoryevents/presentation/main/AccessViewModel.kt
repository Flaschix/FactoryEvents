package com.example.factoryevents.presentation.main

import androidx.lifecycle.ViewModel
import com.example.factoryevents.domain.entity.AccessType
import com.example.factoryevents.domain.usecase.GetUserUseCase
import com.example.factoryevents.extensions.mergeWith
import com.example.factoryevents.presentation.HSE.HseScreenState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class AccessViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase
): ViewModel(){

    private val userFlow = getUserUseCase()

    val screenState = userFlow
        .catch {  }
        .filter { it.rank != AccessType.NONE }
        .map { LoginState.LoggedUser(it) as LoginState }
        .onStart { emit(LoginState.Loading) }

}