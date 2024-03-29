package com.example.factoryevents.presentation.main

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.factoryevents.R
import com.example.factoryevents.domain.entity.AccessType
import com.example.factoryevents.presentation.ViewModelFactory

@Composable
fun AccessScreen(viewModelFactory: ViewModelFactory){

    val viewModel: AccessViewModel = viewModel(factory = viewModelFactory)
    val screenState = viewModel.screenState.collectAsState(LoginState.Initial)

    when(val currentState = screenState.value){
        is LoginState.LoggedUser -> {
            Log.d("MYTEST", "${currentState.user.firstName} ")
            if (currentState.user.rank == AccessType.NONE) {
                UnAccessScreen()
            } else {
                MainScreen(
                    viewModelFactory = viewModelFactory,
                )
            }
        }
        is LoginState.Initial -> {}
        is LoginState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = colorResource(id = R.color.firstColor)),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator(color = colorResource(id = R.color.white))
            }
        }
    }
}

@Composable
fun UnAccessScreen(){
    Text(text = "Error Sign In")
}