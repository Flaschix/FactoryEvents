package com.example.factoryevents.presentation.OJT

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.factoryevents.domain.entity.OJT

@Composable
fun OjtItemScreen(
    ojt: OJT,
    onBackPressedListener: () -> Unit
){

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "OJT")
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
        Column(modifier = Modifier.padding(all = 8.dp)) {
            Text(text = ojt.type)

            Spacer(modifier = Modifier.height(10.dp))

            Text(text = ojt.week)

            Spacer(modifier = Modifier.height(10.dp))

            AsyncImage(
                model = ojt.img,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                contentScale = ContentScale.FillWidth
            )

            Spacer(modifier = Modifier.height(10.dp))
        }

    }
}
