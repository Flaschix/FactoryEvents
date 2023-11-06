package com.example.factoryevents.presentation.main

import com.example.factoryevents.domain.entity.User

sealed class LoginState {
    object Initial: LoginState()

    object Loading: LoginState()

    data class LoggedUser(val user: User): LoginState()
}