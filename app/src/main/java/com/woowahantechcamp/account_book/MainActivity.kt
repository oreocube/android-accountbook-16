package com.woowahantechcamp.account_book

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.woowahantechcamp.account_book.ui.screen.main.AccountBookBottomNavigation
import com.woowahantechcamp.account_book.ui.screen.main.AccountBookScreen
import com.woowahantechcamp.account_book.ui.theme.AccountbookTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AccountBookApp()
        }
    }
}

@Composable
fun AccountBookApp() {
    AccountbookTheme {
        val navController = rememberNavController()
        val currentBackStack by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStack?.destination
        val currentScreen = currentDestination?.route

        Scaffold(
            bottomBar = {
                AccountBookBottomNavigation(
                    modifier = Modifier.fillMaxWidth(),
                    onTabSelected = { screen ->
                        navController.navigateSingleTopTo(screen.route)
                    },
                    currentScreen = currentScreen
                )
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = AccountBookScreen.History.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(route = AccountBookScreen.History.route) {
                    Text(AccountBookScreen.History.route)
                }
                composable(route = AccountBookScreen.Calendar.route) {
                    Text(AccountBookScreen.Calendar.route)
                }
                composable(route = AccountBookScreen.Graph.route) {
                    Text(AccountBookScreen.Graph.route)
                }
                composable(route = AccountBookScreen.Setting.route) {
                    Text(AccountBookScreen.Setting.route)
                }
            }
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AccountbookTheme {
        AccountBookApp()
    }
}