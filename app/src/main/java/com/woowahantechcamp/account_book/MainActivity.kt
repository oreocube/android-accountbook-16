package com.woowahantechcamp.account_book

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.woowahantechcamp.account_book.data.repository.AccountBookDBHelper
import com.woowahantechcamp.account_book.ui.screen.history.HistoryScreen
import com.woowahantechcamp.account_book.ui.screen.main.AccountBookBottomNavigation
import com.woowahantechcamp.account_book.ui.screen.main.AccountBookScreen
import com.woowahantechcamp.account_book.ui.screen.setting.SettingScreen
import com.woowahantechcamp.account_book.ui.theme.AccountbookTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var dbHelper: AccountBookDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AccountBookApp()
        }
    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
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
                    HistoryScreen(year = 2022, month = 7)
                }
                composable(route = AccountBookScreen.Calendar.route) {
                    Text(AccountBookScreen.Calendar.route)
                }
                composable(route = AccountBookScreen.Graph.route) {
                    Text(AccountBookScreen.Graph.route)
                }
                composable(route = AccountBookScreen.Setting.route) {
                    SettingScreen(hiltViewModel())
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