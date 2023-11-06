package com.example.factoryevents.presentation.HSE

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.factoryevents.domain.entity.User
import com.example.factoryevents.domain.entity.WorkerHSE
import com.example.factoryevents.presentation.ViewModelFactory

@Composable
fun HseScreen(
    viewModelFactory: ViewModelFactory,
    user: User
){
    val viewModel: HseScreenViewModel = viewModel(factory = viewModelFactory)
    val screenState = viewModel.screenState.collectAsState(HseScreenState.Initial)


    when(val currentState = screenState.value){
        is HseScreenState.HSE_List -> ShowHSEList(
            list = currentState.list,
            viewModel = viewModel,
        )
        is HseScreenState.Initial -> {}
        is HseScreenState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator(color = Color.Blue)
            }
        }
    }
}

@Composable
private fun ShowHSEList(
    list: List<WorkerHSE>,
    viewModel: HseScreenViewModel,
){
    LazyColumn(
        contentPadding = PaddingValues(top = 10.dp, start = 6.dp, end = 6.dp, bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ){
        items(list, key = {it.id}){ workerHSE ->
            ShowHSERow(workerHSE)
        }
    }
    
}

@Composable
private fun ShowHSERow(
    workerHSE: WorkerHSE,
){
    Text(text = "Man: ${workerHSE.manager}")
}