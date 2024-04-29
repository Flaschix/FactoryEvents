package com.example.factoryevents.presentation.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.factoryevents.R

@Composable
fun LoginScreen(
    onLoginClick: () -> Unit
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.firstColor)),
        contentAlignment = Alignment.Center
    ){
        Column(
            modifier = Modifier.wrapContentHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.pngwing_com__1_),
                contentDescription = null,
                modifier = Modifier
                    .size(300.dp)
                    .clip(CircleShape.copy(all = CornerSize(30)))
                    .border(2.dp, color = colorResource(id = R.color.black), CircleShape.copy(all = CornerSize(30)))
                    .background(colorResource(id = R.color.white))
                    .padding(start = 8.dp, end = 8.dp)

            )

            Spacer(modifier = Modifier.height(300.dp))


            Button(
                onClick = { onLoginClick() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.secondColor)),
                border = BorderStroke(1.dp, color = colorResource(id = R.color.white))
            ) {
                Text(
                    text = stringResource(id = R.string.sign_in),
                    color = colorResource(id = R.color.white)
                )
            }
        }
    }
}