package com.example.factoryevents.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.factoryevents.domain.entity.OJT

class NavigationState(
    val navHostController: NavHostController
){
    fun navigateTo(route: String){
        navHostController.navigate(route){

            launchSingleTop = true

            popUpTo(navHostController.graph.findStartDestination().id){
                saveState = true
            }

            restoreState = true
        }
    }

    fun navigateToFireOrder(){
        navHostController.navigate(Screen.FireOrderFeed.route)
    }

    fun navigateToOjtItem(ojt: OJT){
        navHostController.navigate(Screen.OjtItemFeed.getRouteWithArgs(ojt))
    }
}

@Composable
fun myRememberNavigationState(navHostController: NavHostController = rememberNavController()): NavigationState{
    return remember {
        NavigationState(navHostController)
    }
}