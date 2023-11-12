package com.example.factoryevents.navigation

sealed class Screen(
    val route: String
){

    object HSEFeed : Screen (ROUTE_HSE_SCREEN)
    object OJTFeed : Screen (ROUTE_OJT_SCREEN)
    object OrderFeed : Screen (ROUTE_ORDER_SCREEN)
    object HomeOrder: Screen(ROUTE_HOME_ORDER_SCREEN)
    object FireOrderFeed: Screen(ROUTE_FIRE_ORDER_SCREEN)

    companion object{

        private const val ROUTE_HSE_SCREEN = "HSE_Screen"
        private const val ROUTE_OJT_SCREEN = "OJT_Screen"
        private const val ROUTE_ORDER_SCREEN = "orderScreen"
        private const val ROUTE_HOME_ORDER_SCREEN = "homeOrderScreen"
        private const val ROUTE_FIRE_ORDER_SCREEN = "fireOrderScreen"

        private const val SIGN_IN_SCREEN = "signInScreen"
    }
}
