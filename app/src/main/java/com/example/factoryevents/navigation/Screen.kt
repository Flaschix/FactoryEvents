package com.example.factoryevents.navigation

import android.net.Uri
import com.example.factoryevents.domain.entity.OJT
import com.google.gson.Gson

sealed class Screen(
    val route: String
){

    object HSEFeed : Screen (ROUTE_HSE_SCREEN)

    object OJTFeed : Screen (ROUTE_OJT_SCREEN)
    object HomeOJT: Screen(ROUTE_HOME_OJT_SCREEN)
    object OjtItemFeed: Screen(ROUTE_OJT_ITEM_SCREEN){

        private const val ROUTE_FOR_ARGS = "ojtItemScreen"

        fun getRouteWithArgs(ojt: OJT): String {
            val ojtJson = Gson().toJson(ojt)
            return "$ROUTE_FOR_ARGS/${ ojtJson.encode() }"
        }
    }


    object OrderFeed : Screen (ROUTE_ORDER_SCREEN)
    object HomeOrder: Screen(ROUTE_HOME_ORDER_SCREEN)
    object ReportOrderFeed: Screen(ROUTE_REPORT_ORDER_SCREEN)

    companion object{
        const val KEY_OJT = "keyOJT"

        private const val ROUTE_HSE_SCREEN = "HSE_Screen"

        private const val ROUTE_OJT_SCREEN = "OJT_Screen"
        private const val ROUTE_HOME_OJT_SCREEN = "homeOJTScreen"
        private const val ROUTE_OJT_ITEM_SCREEN = "ojtItemScreen/{$KEY_OJT}"

        private const val ROUTE_ORDER_SCREEN = "orderScreen"
        private const val ROUTE_HOME_ORDER_SCREEN = "homeOrderScreen"
        private const val ROUTE_REPORT_ORDER_SCREEN = "reportOrderScreen"

        private const val SIGN_IN_SCREEN = "signInScreen"
    }
}

fun String.encode(): String{
    return Uri.encode(this)
}
