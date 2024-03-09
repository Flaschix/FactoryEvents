package com.example.factoryevents.presentation.OJT

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.factoryevents.R
import com.example.factoryevents.domain.entity.OJT

@Composable
fun OjtItemScreen(
    ojt: OJT,
    onBackPressedListener: () -> Unit
){

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = stringResource(id = R.string.ojt))
            },
                navigationIcon = {
                    IconButton(onClick = { onBackPressedListener() }) {
                        Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = null)
                    }
                }
            )
        }

    ){
        it
        OjtInformation(ojt, modifier = Modifier)
    }
}

@Composable
private fun OjtInformation(
    ojt: OJT, modifier: Modifier = Modifier
){
    Card(
        modifier = modifier,
    ){
        Column(
            modifier = Modifier
                .background(colorResource(id = R.color.firstColor))
                .padding(all = 8.dp)
                .verticalScroll(rememberScrollState())
        ) {

            Header(ojt.type, ojt.week, ojt.dueDate)

            Spacer(modifier = Modifier.height(10.dp))

            AsyncImage(
                model = "https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEhC5mIuy2KaMll2PVI4iE_18w4c7zDCcSi0PzWijCWeakJEJRGhAnIaSkYGLK7dEYlTwYnqTzwOJJcRxBrNFrZMsRovChY8CVpVIXH5pNPPj5wo1kPfGeth4z690xixWqd69vceT1yMaxGB4nDNXUnQ-kuJm3yHgqJreLneAV0nWp4lsF-BPFX0CgM8Tw/w1200-h630-p-k-no-nu/card%20view.jpg",
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .wrapContentHeight(),
                contentScale = ContentScale.FillWidth
            )

            Spacer(modifier = Modifier.height(10.dp))

            Describe(ojt.offence)

            Spacer(modifier = Modifier.height(10.dp))

            Bottom(ojt.place, ojt.pilot, ojt.areResponsible, ojt.byWhomOpened, ojt.options, ojt.status)

            Spacer(modifier = Modifier.height(100.dp))
        }

    }
}

@Composable
private fun Header(type: String, week: String, dueDate: String){
    Row( modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Тип: $type | Неделя: $week",
            color = colorResource(id = R.color.white)
        )
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = stringResource(id = R.string.ojt_due_date),
                color = colorResource(id = R.color.white)
            )
            Text(
                text = dueDate,
                color = colorResource(id = R.color.white)
            )
        }

    }
}

@Composable
private fun Describe(text: String = ""){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(colorResource(id = R.color.secondColor))
            .padding(top = 4.dp, bottom = 10.dp, start = 4.dp, end = 4.dp)

    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = stringResource(id = R.string.ojt_describe),
            color = colorResource(id = R.color.white)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = text,
            color = colorResource(id = R.color.white)
        )
    }
}

@Composable
private fun Bottom(
    place: String,
    pilot: String,
    areResponsible: String,
    whoOpened: String,
    options: String,
    status: Boolean
){
    Text(
        text = stringResource(id = R.string.ojt_place) + " $place",
        color = colorResource(id = R.color.white)
    )

    Spacer(modifier = Modifier.height(10.dp))

    Text(
        text = stringResource(id = R.string.ojt_pilot) + " $pilot",
        color = colorResource(id = R.color.white)
    )

    Spacer(modifier = Modifier.height(10.dp))

    Text(
        text = stringResource(id = R.string.ojt_responsibility) + " $areResponsible",
        color = colorResource(id = R.color.white)
    )

    Spacer(modifier = Modifier.height(10.dp))

    Text(
        text = stringResource(id = R.string.ojt_who_opened) + " $whoOpened",
        color = colorResource(id = R.color.white)
    )

    Spacer(modifier = Modifier.height(10.dp))

    Text(
        text = stringResource(id = R.string.ojt_options) + " $options",
        color = colorResource(id = R.color.white)
    )

    Spacer(modifier = Modifier.height(10.dp))

    Text(
        text = stringResource(id = R.string.ojt_status) + " " +
                if (status) stringResource(id = R.string.ojt_status_true)
                else stringResource(id = R.string.ojt_status_false),
        color = colorResource(id = R.color.white)
    )
}
