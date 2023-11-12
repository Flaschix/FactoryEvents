package com.example.factoryevents.presentation.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.factoryevents.R
import com.example.factoryevents.navigation.Screen

sealed class NavigationItem(
    val screen: Screen,
    val resId: Int,
    val icon: ImageVector
){
    object HSEFeed: NavigationItem(
        screen = Screen.HSEFeed,
        resId = R.string.hse,
        icon = Icons.Outlined.Home
    )

    object OJTFeed: NavigationItem(
        screen = Screen.OJTFeed,
        resId = R.string.ojt,
        icon = Icons.Outlined.Favorite
    )

    object OrderFeed: NavigationItem(
        screen = Screen.HomeOrder,
        resId = R.string.make_request,
        icon = Icons.Outlined.Person
    )
}
