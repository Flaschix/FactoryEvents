package com.example.factoryevents.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    HSE_ScreenContent: @Composable () -> Unit,
    OJT_ScreenContent: @Composable () -> Unit,
    makeRequestScreenContent: @Composable () -> Unit
){
    NavHost(navController = navHostController, startDestination = Screen.HSEFeed.route) {
        composable(Screen.HSEFeed.route) {
            HSE_ScreenContent()
        }

        composable(Screen.OJTFeed.route) {
            OJT_ScreenContent()
        }
        composable(Screen.makeRequestFeed.route) {
            makeRequestScreenContent()
        }
    }
}