package com.example.factoryevents.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.orderScreenNavGraph(
    mainOrderScreen: @Composable () -> Unit,
    fireOrderScreen: @Composable () -> Unit,
){
    navigation(
        startDestination = Screen.OrderFeed.route,
        route = Screen.HomeOrder.route,

        builder = {
            composable(Screen.OrderFeed.route){
                mainOrderScreen()
            }

            composable(Screen.FireOrderFeed.route){
                fireOrderScreen()
            }
        }
    )
}