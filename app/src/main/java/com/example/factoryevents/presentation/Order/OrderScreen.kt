package com.example.factoryevents.presentation.Order

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.factoryevents.R
import com.example.factoryevents.presentation.ViewModelFactory

@Composable
fun OrderScreen(
    viewModelFactory: ViewModelFactory,
    onReportClickListener: () -> Unit
){
    val viewModel: OrderScreenViewModel = viewModel(factory = viewModelFactory)

    OrderList(
        onReportClickListener = onReportClickListener
    )
}

@Composable
fun OrderList(
    onReportClickListener: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            onClick = { onReportClickListener() },
        ) {
            Text(
                text = stringResource(id = R.string.order_fire),
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            onClick = {  },
        ) {
            Text(
                text = "Второе",
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            onClick = {  },
        ) {
            Text(
                text = "Третье",
            )
        }
    }
}