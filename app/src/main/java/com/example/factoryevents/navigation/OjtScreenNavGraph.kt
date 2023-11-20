package com.example.factoryevents.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.factoryevents.domain.entity.OJT
import com.google.gson.Gson

fun NavGraphBuilder.ojtItemScreenNavGraph(
    mainOjtScreen: @Composable () -> Unit,
    ojtItemScreen: @Composable (ojt: OJT) -> Unit,
){
    navigation(
        startDestination = Screen.OJTFeed.route,
        route = Screen.HomeOJT.route,

        builder = {
            composable(Screen.OJTFeed.route){
                mainOjtScreen()
            }

            composable(
                route = Screen.OjtItemFeed.route,
                arguments = listOf(
                    navArgument(Screen.KEY_OJT){
                        type = NavType.StringType
                    }
                )
            ){
                val ojtJson = it.arguments?.getString(Screen.KEY_OJT) ?: ""

                val ojt = Gson().fromJson<OJT>(ojtJson, OJT::class.java)
                ojtItemScreen(ojt)
            }
        }
    )
}