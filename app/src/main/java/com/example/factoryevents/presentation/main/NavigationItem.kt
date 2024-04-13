package com.example.factoryevents.presentation.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.example.factoryevents.R
import com.example.factoryevents.navigation.Screen

sealed class NavigationItem(
    val screen: Screen,
    val resId: Int,
    val icon: Int
){
    object HSEFeed: NavigationItem(
        screen = Screen.HSEFeed,
        resId = R.string.hse,
        icon = R.drawable.audits_24
    )

    object OJTFeed: NavigationItem(
        screen = Screen.HomeOJT,
        resId = R.string.ojt,
        icon = R.drawable.insidents_24
    )

    object OrderFeed: NavigationItem(
        screen = Screen.HomeOrder,
        resId = R.string.make_request,
        icon = R.drawable.report_24
    )
}
