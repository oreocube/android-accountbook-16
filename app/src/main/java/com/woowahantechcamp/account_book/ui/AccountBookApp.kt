package com.woowahantechcamp.account_book.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.woowahantechcamp.account_book.ui.screen.history.HistoryScreen
import com.woowahantechcamp.account_book.ui.screen.history.HistoryViewModel
import com.woowahantechcamp.account_book.ui.screen.main.AccountBookBottomNavigation
import com.woowahantechcamp.account_book.ui.screen.main.AccountBookScreen
import com.woowahantechcamp.account_book.ui.screen.setting.SettingDetail
import com.woowahantechcamp.account_book.ui.screen.setting.SettingType
import com.woowahantechcamp.account_book.ui.screen.setting.SettingScreen
import com.woowahantechcamp.account_book.ui.screen.setting.SettingViewModel
import com.woowahantechcamp.account_book.ui.theme.AccountbookTheme

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AccountBookApp(
    historyViewModel: HistoryViewModel = hiltViewModel(),
    settingViewModel: SettingViewModel = hiltViewModel()
) {
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
                    HistoryScreen(historyViewModel)
                }
                composable(route = AccountBookScreen.Calendar.route) {
                    Text(AccountBookScreen.Calendar.route)
                }
                composable(route = AccountBookScreen.Graph.route) {
                    Text(AccountBookScreen.Graph.route)
                }
                composable(route = AccountBookScreen.Setting.route) {
                    SettingScreen(
                        viewModel = settingViewModel,
                        onItemClicked = { type, id ->
                            navController.navigate(route = "${MainDestinations.SETTING_DETAIL_ROUTE}/$type/$id")
                        },
                        onAddClick = {
                            navController.navigate(route = "${MainDestinations.SETTING_DETAIL_ROUTE}/add/$it")
                        }
                    )
                }
                composable(
                    route = "${MainDestinations.SETTING_DETAIL_ROUTE}/{type}/{id}",
                    arguments = listOf(
                        navArgument("type") { type = NavType.EnumType(SettingType::class.java) },
                        navArgument("id") { type = NavType.IntType }
                    )
                ) { backStackEntry ->
                    val type = backStackEntry.arguments?.get("type") as SettingType
                    val id = backStackEntry.arguments?.get("id") as Int // 아이템 조회시 필요 TODO

                    SettingDetail(
                        viewModel = settingViewModel,
                        title = type.addTitle,
                        id = id,
                        type = type,
                        onUpPressed = { navController.navigateUp() },
                        onSaved = { navController.navigateUp() }
                    )
                }
                composable(
                    route = "${MainDestinations.SETTING_DETAIL_ROUTE}/add/{type}",
                    arguments = listOf(navArgument("type") {
                        type = NavType.EnumType(SettingType::class.java)
                    })
                ) { backStackEntry ->
                    val type = backStackEntry.arguments?.get("type") as SettingType

                    SettingDetail(
                        viewModel = settingViewModel,
                        title = type.addTitle,
                        type = type,
                        onUpPressed = { navController.navigateUp() },
                        onSaved = { navController.navigateUp() }
                    )
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