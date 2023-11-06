package com.example.factoryevents.presentation.OJT

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
import com.example.factoryevents.domain.entity.OJT
import com.example.factoryevents.presentation.ViewModelFactory

@Composable
fun OjtScreen(
    viewModelFactory: ViewModelFactory,
){
    val viewModel: OjtScreenViewModel = viewModel(factory = viewModelFactory)
    val screenState = viewModel.screenState.collectAsState(OjtScreenState.Initial)


    when(val currentState = screenState.value){
        is OjtScreenState.OJT_List -> ShowOjtList(
            list = currentState.list,
            viewModel = viewModel,
        )
        is OjtScreenState.Initial -> {}
        is OjtScreenState.Loading -> {
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
private fun ShowOjtList(
    list: List<OJT>,
    viewModel: OjtScreenViewModel,
){
    LazyColumn(
        contentPadding = PaddingValues(top = 10.dp, start = 6.dp, end = 6.dp, bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ){
        items(list, key = {it.id}){ ojt ->
            ShowOjtRow(ojt)
        }
    }
}

@Composable
private fun ShowOjtRow(
    ojt: OJT,
){
    Text(text = "Man: ${ojt.byWhomOpened}")
}