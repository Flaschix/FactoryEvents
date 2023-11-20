package com.example.factoryevents.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.factoryevents.domain.entity.OJT

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    HSE_ScreenContent: @Composable () -> Unit,

    OJT_ScreenContent: @Composable () -> Unit,
    OJT_ItemScreenContent: @Composable (ojt: OJT) -> Unit,

    orderScreenContent: @Composable () -> Unit,
    fireOrderScreenContent: @Composable () -> Unit
){
    NavHost(navController = navHostController, startDestination = Screen.HSEFeed.route) {
        composable(Screen.HSEFeed.route) {
            HSE_ScreenContent()
        }

//        composable(Screen.OJTFeed.route) {
//            OJT_ScreenContent()
//        }
        ojtItemScreenNavGraph(
            mainOjtScreen = OJT_ScreenContent,
            ojtItemScreen = OJT_ItemScreenContent
        )

        orderScreenNavGraph(
            mainOrderScreen = orderScreenContent,
            fireOrderScreen = fireOrderScreenContent
        )
    }
}