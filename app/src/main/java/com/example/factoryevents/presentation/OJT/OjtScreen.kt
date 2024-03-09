package com.example.factoryevents.presentation.OJT

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.factoryevents.R
import com.example.factoryevents.domain.entity.OJT
import com.example.factoryevents.presentation.ViewModelFactory

@Composable
fun OjtScreen(
    viewModelFactory: ViewModelFactory,
    onOjtClickListener: (ojt: OJT) -> Unit
){
    val viewModel: OjtScreenViewModel = viewModel(factory = viewModelFactory)
    val screenState = viewModel.screenState.collectAsState(OjtScreenState.Initial)


    when(val currentState = screenState.value){
        is OjtScreenState.OJT_List -> ShowOjtList(
            list = currentState.list,
            viewModel = viewModel,
            onClickListener = onOjtClickListener
        )
        is OjtScreenState.Initial -> {}
        is OjtScreenState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(id = R.color.firstColor)),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator(
                    color = colorResource(id = R.color.white)
                )
            }
        }
    }
}

@Composable
private fun ShowOjtList(
    list: List<OJT>,
    viewModel: OjtScreenViewModel,
    onClickListener: (ojt: OJT) -> Unit
){
    LazyColumn(
        contentPadding = PaddingValues(top = 10.dp, start = 6.dp, end = 6.dp, bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier.background(colorResource(id = R.color.firstColor))
    ){
        items(list, key = {it.id}){ ojt ->
//            Log.d("OJT_TEST", "${ojt.img}: ${ojt.status}, id: ${ojt.id}")
            OjtItem(
                ojt = ojt,
                onClickListener = {
                    onClickListener(ojt)
                }
            )
        }
    }
}

@Composable
private fun OjtItem(
    ojt: OJT,
    onClickListener: (ojt: OJT) -> Unit
){

    Card(
        modifier = Modifier
            .clip(RectangleShape)
            .padding(4.dp)
            .clickable(
                onClick = {
                    onClickListener(ojt)
                }
            )
    ) {
        Header(ojt)
    }
}

@Composable
fun Header(ojt: OJT){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (ojt.status) colorResource(id = R.color.teal_200) else colorResource(R.color.pink))
            .padding(horizontal = 3.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier
            .weight(2f)
        ) {
            Text(
                text = "Тип: ${ojt.type}",
                color = colorResource(id = R.color.black)
            )

//            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = "Неделя: ${ojt.week}",
                color = colorResource(id = R.color.black)
            )
            Spacer(modifier = Modifier.height(3.dp))

                IconNearText(
                iconId = R.drawable.pilot_24,
                text = ojt.pilot,
            )

            Spacer(modifier = Modifier.height(3.dp))

            IconNearText(
                iconId = R.drawable.place_24,
                text = ojt.place,
            )

            Spacer(modifier = Modifier.height(3.dp))

            IconNearText(
                iconId = R.drawable.responsible_24,
                text = ojt.areResponsible,
            )

        }

        Column(
            modifier = Modifier
                .weight(1f),
//            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Icon(
                modifier = Modifier.size(30.dp),
                painter = painterResource(id = R.drawable.timer_24),
                tint = colorResource(id = R.color.black),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = ojt.dueDate,
                color = colorResource(id = R.color.black)
            )
        }

        AsyncImage(
            model = "https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEhC5mIuy2KaMll2PVI4iE_18w4c7zDCcSi0PzWijCWeakJEJRGhAnIaSkYGLK7dEYlTwYnqTzwOJJcRxBrNFrZMsRovChY8CVpVIXH5pNPPj5wo1kPfGeth4z690xixWqd69vceT1yMaxGB4nDNXUnQ-kuJm3yHgqJreLneAV0nWp4lsF-BPFX0CgM8Tw/w1200-h630-p-k-no-nu/card%20view.jpg",
            modifier = Modifier
                .weight(2f)
                .fillMaxWidth()
                .padding(vertical = 10.dp)
                .clip(CircleShape),
            contentDescription = ""
        )
    }
}

@Composable
fun IconNearText(
    iconId: Int,
    text: String,
    itemClickListener: (() -> Unit)? = null,
    tint: Color = colorResource(id = R.color.black),
    textColor: Color = colorResource(id = R.color.black)
) {
    val modifier = if(itemClickListener == null) Modifier
    else Modifier.clickable { itemClickListener() }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            painter = painterResource(id = iconId),
            contentDescription = null,
            tint = tint
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = text,

            color = textColor
        )
    }
}