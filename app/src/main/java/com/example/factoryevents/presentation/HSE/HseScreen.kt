package com.example.factoryevents.presentation.HSE

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.rememberSwipeableState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.factoryevents.R
import com.example.factoryevents.domain.entity.User
import com.example.factoryevents.domain.entity.WorkerHSE
import com.example.factoryevents.presentation.ViewModelFactory
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun HseScreen(
    viewModelFactory: ViewModelFactory,
){
    val viewModel: HseScreenViewModel = viewModel(factory = viewModelFactory)
    val screenState = viewModel.screenState.collectAsState(HseScreenState.Initial)

    when(val currentState = screenState.value){
        is HseScreenState.HSE_List -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = colorResource(id = R.color.firstColor))

            ){
                ShowHSEList(
                    list = currentState.list,
                    viewModel = viewModel,
                )

//                Button(
//                    onClick = { /*TODO*/ },
//                    modifier = Modifier.align(Alignment.TopEnd)
//                ) {
//                    Icon(painter = painterResource(id = R.drawable.timer_24), contentDescription = "")
//                }
            }

        }
        is HseScreenState.Initial -> {}
        is HseScreenState.Loading -> {
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ShowHSEList(
    list: List<WorkerHSE>,
    viewModel: HseScreenViewModel,
){
    val loadState by viewModel.isLoading.collectAsState()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = loadState)
    var currentSortTypeOwn by remember { mutableStateOf<SortTypeOwn>(SortTypeOwn.All) }

    val sortedList = when (currentSortTypeOwn){
        SortTypeOwn.All -> list
        SortTypeOwn.Mine -> list.filter { it.manager == "evgeniy saleev" }
    }

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = viewModel::refresh
    ) {
        LazyColumn(
            contentPadding = PaddingValues(top = 10.dp, bottom = 100.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.background(colorResource(id = R.color.firstColor))
        ){
            item {
                SortDisplay(
                    sortTypeOwn = currentSortTypeOwn,
                    onNext = { currentSortTypeOwn = currentSortTypeOwn.getNext() },
                    onPrevious = { currentSortTypeOwn = currentSortTypeOwn.getNext() }
                )
            }
            items(sortedList, key = {it.id}){ workerHSE ->
                ShowHSERow(
                    workerHSE,
                    modifier = Modifier.animateItemPlacement()
                )
            }
        }
    }

}

@Composable
private fun ShowHSERow(
    workerHSE: WorkerHSE,
    modifier: Modifier
){

    Column(
        modifier = modifier
            .background(colorResource(id = R.color.secondColor), RoundedCornerShape(10.dp))
            .border(1.dp, color = colorResource(id = R.color.white), RoundedCornerShape(10.dp))
            .padding(start = 4.dp, end = 4.dp)
    ) {

        Spacer(modifier = Modifier.height(3.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Имя: ${workerHSE.manager}",
                color = colorResource(id = R.color.white)
            )
            Text(
                text = "Позиция: ${workerHSE.supervisor}",
                color = colorResource(id = R.color.white)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,

            ) {
            HSE_Component(
                stringResource(id = R.string.planned_line),
                workerHSE.listOfHSE[0].plannedLine,
                workerHSE.listOfHSE[1].plannedLine,
                workerHSE.listOfHSE[2].plannedLine
            )

            HSE_Component(
                stringResource(id = R.string.done_on_line),
                workerHSE.listOfHSE[0].doneOnLine,
                workerHSE.listOfHSE[1].doneOnLine,
                workerHSE.listOfHSE[2].doneOnLine,
                workerHSE.listOfHSE[0].doneOnLine != "",
                workerHSE.listOfHSE[1].doneOnLine != "",
                workerHSE.listOfHSE[2].doneOnLine != "",
            )

            HSE_Component(
                stringResource(id = R.string.score),
                workerHSE.listOfHSE[0].score,
                workerHSE.listOfHSE[1].score,
                workerHSE.listOfHSE[2].score
            )

            HSE_Component(
                stringResource(id = R.string.date),
                workerHSE.listOfHSE[0].date,
                workerHSE.listOfHSE[1].date,
                workerHSE.listOfHSE[2].date
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "",
                    color = colorResource(id = R.color.white)
                )
                Text(
                    text = "CV19",
                    color = colorResource(id = R.color.white)
                )
                Text(
                    text = "SAF",
                    color = colorResource(id = R.color.white)
                )
                Text(
                    text = "SGR",
                    color = colorResource(id = R.color.white)
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))
    }


}

@Composable
private fun HSE_Component(
    title: String,
    covid: String,
    saf: String,
    sgr: String,
    notDoneCovid: Boolean = true,
    notDoneSaf: Boolean = true,
    notDoneSgr: Boolean = true,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .border(1.dp, color = colorResource(id = R.color.white), RoundedCornerShape(10.dp))
            .padding(start = 4.dp, end = 4.dp)
    ) {
        Text(
            text = title,
            color = colorResource(id = R.color.white)
        )
        Text(
            text = covid,
            color = colorResource(id = R.color.white),
            modifier = if(notDoneCovid) Modifier else Modifier
                .size(width = 100.dp, height = 20.dp)
                .background(colorResource(id = R.color.pink), shape = RoundedCornerShape(10.dp))
        )
        Text(
            text = saf,
            color = colorResource(id = R.color.white),
            modifier = if(notDoneSaf) Modifier else Modifier
                .size(width = 100.dp, height = 20.dp)
                .background(colorResource(id = R.color.pink), shape = RoundedCornerShape(10.dp))
        )
        Text(
            text = if(!notDoneSgr) "-" else sgr,
            color = colorResource(id = R.color.white),
            modifier = if(notDoneSgr) Modifier else Modifier
                .width(80.dp)
                .padding(bottom = 3.dp)
                .background(colorResource(id = R.color.pink), shape = RoundedCornerShape(10.dp))
        )
    }
}

@Composable
private fun HSE_Component(
    title: String,
    covid: String,
    saf: String,
    sgr: String,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .border(1.dp, color = colorResource(id = R.color.white), RoundedCornerShape(10.dp))
            .padding(start = 4.dp, end = 4.dp)
    ) {
        Text(
            text = title,
            color = colorResource(id = R.color.white)
        )
        Text(
            text = covid,
            color = colorResource(id = R.color.white),
        )
        Text(
            text = saf,
            color = colorResource(id = R.color.white),
        )
        Text(
            text = sgr,
            color = colorResource(id = R.color.white),
        )
    }
}

@Composable
private fun SortDisplay(
    sortTypeOwn: SortTypeOwn,
    onNext : () -> Unit,
    onPrevious: () -> Unit
){
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .background(colorResource(id = R.color.secondColor), RoundedCornerShape(10.dp))
                .border(1.dp, colorResource(id = R.color.white), RoundedCornerShape(10.dp))
                .fillMaxHeight()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(10.dp))
                .clickable { onPrevious() }
        ){
            Icon(
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = "Back",
                tint = colorResource(id = R.color.white),
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Text(
            text = "Список: ${sortTypeOwn.name}",
            color = colorResource(id = R.color.white),
            modifier = Modifier
                .background(colorResource(id = R.color.secondColor),RoundedCornerShape(10.dp))
                .border(1.dp, colorResource(id = R.color.white), RoundedCornerShape(10.dp))
                .padding(horizontal = 20.dp, vertical = 12.dp)
        )

        Box(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .background(colorResource(id = R.color.secondColor), RoundedCornerShape(10.dp))
                .border(1.dp, colorResource(id = R.color.white), RoundedCornerShape(10.dp))
                .fillMaxHeight()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(10.dp))
                .clickable { onNext() }
        ){
            Icon(
                imageVector = Icons.Rounded.ArrowForward,
                contentDescription = "Forward",
                tint = colorResource(id = R.color.white),
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}