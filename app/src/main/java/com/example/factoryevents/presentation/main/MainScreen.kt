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
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.factoryevents.navigation.AppNavGraph
import com.example.factoryevents.navigation.myRememberNavigationState

@Preview
@Composable
fun MainScreen(    /*viewModel: ViewModelFactory*/    ){

    val navigationState = myRememberNavigationState()

    Scaffold(
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()

                val items = listOf(
                    NavigationItem.HSEFeed,
                    NavigationItem.OJTFeed,
                    NavigationItem.makeRequestFeed
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
            HSE_ScreenContent = { TextCounter(text = "HSE") },
            OJT_ScreenContent = { TextCounter(text = "OJT")  },
            makeRequestScreenContent = { TextCounter(text = "makeRequest") }
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