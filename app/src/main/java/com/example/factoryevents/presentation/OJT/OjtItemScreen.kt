package com.example.factoryevents.presentation.OJT

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
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
                .padding(all = 8.dp)
        ) {

            Header(ojt.type, ojt.week, ojt.dueDate)

            Spacer(modifier = Modifier.height(10.dp))

            AsyncImage(
                model = ojt.img,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .border(2.dp, Color.Red, RectangleShape),
                contentScale = ContentScale.FillWidth
            )

            Spacer(modifier = Modifier.height(10.dp))

            Describe(ojt.offence)

            Spacer(modifier = Modifier.height(10.dp))

            Bottom(ojt.place, ojt.pilot, ojt.areResponsible, ojt.byWhomOpened, ojt.options, ojt.status)
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
            color = MaterialTheme.colors.onSurface
        )

        Text(
            text = stringResource(id = R.string.ojt_due_date) + " $dueDate",
            color = MaterialTheme.colors.onSurface
        )
    }
}

@Composable
private fun Describe(text: String = ""){
    Text(
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        text = stringResource(id = R.string.ojt_describe)
    )

    Spacer(modifier = Modifier.height(10.dp))

    Text(text = text)
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
        text = stringResource(id = R.string.ojt_place) + " $place"
    )

    Spacer(modifier = Modifier.height(10.dp))

    Text(
        text = stringResource(id = R.string.ojt_pilot) + " $pilot"
    )

    Spacer(modifier = Modifier.height(10.dp))

    Text(
        text = stringResource(id = R.string.ojt_responsibility) + " $areResponsible"
    )

    Spacer(modifier = Modifier.height(10.dp))

    Text(
        text = stringResource(id = R.string.ojt_who_opened) + " $whoOpened"
    )

    Spacer(modifier = Modifier.height(10.dp))

    Text(
        text = stringResource(id = R.string.ojt_options) + " $options"
    )

    Spacer(modifier = Modifier.height(10.dp))

    Text(
        text = stringResource(id = R.string.ojt_status) + " " +
                if (status) stringResource(id = R.string.ojt_status_true)
                else stringResource(id = R.string.ojt_status_false)
    )
}
