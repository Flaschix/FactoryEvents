package com.example.factoryevents.presentation.HSE

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.factoryevents.R
import com.example.factoryevents.domain.entity.User
import com.example.factoryevents.domain.entity.WorkerHSE
import com.example.factoryevents.presentation.ViewModelFactory

@Composable
fun HseScreen(
    viewModelFactory: ViewModelFactory,
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
            
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
    
}

@Composable
private fun ShowHSERow(
    workerHSE: WorkerHSE,
){
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Name:")
            Text(text = workerHSE.manager)
            Text(text = "Supervisor:")
            Text(text = workerHSE.supervisor)
        }
        Row(
            horizontalArrangement = Arrangement.Center,

        ) {
            HSE_Component(
                stringResource(id = R.string.planned_line),
                workerHSE.listOfHSE[0].plannedLine,
                workerHSE.listOfHSE[1].plannedLine,
                workerHSE.listOfHSE[2].plannedLine
            )

            Spacer(modifier = Modifier.padding(start = 8.dp))

            HSE_Component(
                stringResource(id = R.string.done_on_line),
                workerHSE.listOfHSE[0].doneOnLine,
                workerHSE.listOfHSE[1].doneOnLine,
                workerHSE.listOfHSE[2].doneOnLine
            )

            Spacer(modifier = Modifier.padding(start = 8.dp))

            HSE_Component(
                stringResource(id = R.string.score),
                workerHSE.listOfHSE[0].score,
                workerHSE.listOfHSE[1].score,
                workerHSE.listOfHSE[2].score
            )

            Spacer(modifier = Modifier.padding(start = 8.dp))

            HSE_Component(
                stringResource(id = R.string.date),
                workerHSE.listOfHSE[0].date,
                workerHSE.listOfHSE[1].date,
                workerHSE.listOfHSE[2].date
            )

            Spacer(modifier = Modifier.padding(start = 8.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(text = "")
                Text(text = "CV19")
                Text(text = "SAF")
                Text(text = "SGR")
            }
        }
    }
}

@Composable
private fun HSE_Component(
    title: String,
    covid: String,
    saf: String,
    sgr: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .border(1.dp, Color.Black)
            .padding(2.dp)
    ) {
        Text(text = title)
        Text(text = covid)
        Text(text = saf)
        Text(text = sgr)
    }

}