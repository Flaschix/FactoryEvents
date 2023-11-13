package com.example.factoryevents.presentation.main

import androidx.compose.foundation.clickable
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.factoryevents.navigation.AppNavGraph
import com.example.factoryevents.navigation.myRememberNavigationState
import com.example.factoryevents.presentation.FireOrder.FireOrder
import com.example.factoryevents.presentation.HSE.HseScreen
import com.example.factoryevents.presentation.OJT.OjtScreen
import com.example.factoryevents.presentation.Order.OrderScreen
import com.example.factoryevents.presentation.ViewModelFactory

@Composable
fun MainScreen(
    viewModelFactory: ViewModelFactory,
){

    val navigationState = myRememberNavigationState()

    Scaffold(
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()

                val items = listOf(
                    NavigationItem.HSEFeed,
                    NavigationItem.OJTFeed,
                    NavigationItem.OrderFeed
                )

                items.forEach { item ->

                    val selected = navBackStackEntry?.destination?.hierarchy?.any {
                        it.route == item.screen.route
                    } ?: false

                    BottomNavigationItem(
                        selected = selected,
                        onClick = {
                            if(!selected) navigationState.navigateTo(item.screen.route)
                        },
                        icon = { Icon(item.icon, contentDescription = null) },
                        label = { Text(text = stringResource(id = item.resId)) }
                    )
                }
            }
        },

    ) {
        it

        AppNavGraph(
            navHostController = navigationState.navHostController,
            HSE_ScreenContent = { HseScreen(viewModelFactory) },
            OJT_ScreenContent = { OjtScreen(viewModelFactory)  },
            orderScreenContent = { OrderScreen(
                viewModelFactory,
                onFireClickListener = {
                    navigationState.navigateToFireOrder()
                }
            ) },
            fireOrderScreenContent = { FireOrder(
                onBackPressedListener = { navigationState.navHostController.popBackStack() },
            ) }
        )
    }
}

@Composable
private fun TextCounter(text: String){
    var count by rememberSaveable {
        mutableStateOf(0)
    }

    Text(
        text = "$text Count: $count",
        modifier = Modifier.clickable { count++ },
    )
}