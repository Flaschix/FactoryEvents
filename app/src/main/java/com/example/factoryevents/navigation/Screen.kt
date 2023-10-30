package com.example.factoryevents.navigation

import android.net.Uri

sealed class Screen(
    val route: String
){

    object HSEFeed : Screen (ROUTE_HSE_SCREEN)
    object OJTFeed : Screen (ROUTE_OJT_SCREEN)
    object makeRequestFeed : Screen (ROUTE_MAKE_REQUEST_SCREEN)

    companion object{

        private const val ROUTE_HSE_SCREEN = "HSE_Screen"
        private const val ROUTE_OJT_SCREEN = "OJT_Screen"
        private const val ROUTE_MAKE_REQUEST_SCREEN = "makeRequestScreen"

        private const val SIGN_IN_SCREEN = "signInScreen"
    }
}
