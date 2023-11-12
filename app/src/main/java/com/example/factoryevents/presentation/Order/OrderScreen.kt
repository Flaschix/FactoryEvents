package com.example.factoryevents.presentation.Order

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.factoryevents.presentation.ViewModelFactory

@Composable
fun OrderScreen(
    viewModelFactory: ViewModelFactory,
    onFireClickListener: () -> Unit
){
    val viewModel: OrderScreenViewModel = viewModel(factory = viewModelFactory)

    OrderList(
        onFireClickListener = onFireClickListener
    )
}

@Composable
fun OrderList(
    onFireClickListener: () -> Unit
){
    Box(
        modifier = Modifier.size(100.dp),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = "FireOrder",
            modifier = Modifier.clickable { onFireClickListener() },
            )
    }
}