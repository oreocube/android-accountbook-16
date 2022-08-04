package com.woowahantechcamp.account_book.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.woowahantechcamp.account_book.ui.DestinationsArgs.ID_ARG
import com.woowahantechcamp.account_book.ui.DestinationsArgs.TYPE_ARG
import com.woowahantechcamp.account_book.ui.MainDestinations.HISTORY_DETAIL_ROUTE
import com.woowahantechcamp.account_book.ui.MainDestinations.SETTING_DETAIL_ROUTE
import com.woowahantechcamp.account_book.ui.Screens.HISTORY_SCREEN
import com.woowahantechcamp.account_book.ui.Screens.SETTING_SCREEN
import com.woowahantechcamp.account_book.ui.model.Type
import com.woowahantechcamp.account_book.ui.screen.calendar.CalendarScreen
import com.woowahantechcamp.account_book.ui.screen.graph.GraphScreen
import com.woowahantechcamp.account_book.ui.screen.history.HistoryDetail
import com.woowahantechcamp.account_book.ui.screen.history.HistoryScreen
import com.woowahantechcamp.account_book.ui.screen.main.AccountBookBottomNavigation
import com.woowahantechcamp.account_book.ui.screen.main.AccountBookScreen
import com.woowahantechcamp.account_book.ui.screen.main.MainViewModel
import com.woowahantechcamp.account_book.ui.screen.setting.SettingDetail
import com.woowahantechcamp.account_book.ui.screen.setting.SettingScreen
import com.woowahantechcamp.account_book.ui.screen.setting.SettingType
import com.woowahantechcamp.account_book.ui.screen.setting.SettingViewModel
import com.woowahantechcamp.account_book.ui.theme.AccountbookTheme

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AccountBookApp(
    mainViewModel: MainViewModel = hiltViewModel(),
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
                    HistoryScreen(
                        mainViewModel = mainViewModel,
                        viewModel = hiltViewModel(),
                        onHistoryItemClick = { type, id ->
                            navController.navigateToAddEditHistory(type, id)
                        },
                        onAddClick = { type ->
                            navController.navigateToAddEditHistory(type)
                        }
                    )
                }
                composable(route = AccountBookScreen.Calendar.route) {
                    CalendarScreen(mainViewModel = mainViewModel)
                }
                composable(route = AccountBookScreen.Graph.route) {
                    GraphScreen(mainViewModel = mainViewModel)
                }
                composable(route = AccountBookScreen.Setting.route) {
                    SettingScreen(
                        viewModel = settingViewModel,
                        onItemClicked = { type, id ->
                            navController.navigateToAddEditSetting(type, id)
                        },
                        onAddClick = { type ->
                            navController.navigateToAddEditSetting(type)
                        }
                    )
                }
                composable(
                    route = SETTING_DETAIL_ROUTE,
                    arguments = listOf(
                        navArgument(TYPE_ARG) { type = NavType.EnumType(SettingType::class.java) },
                        navArgument(ID_ARG) { type = NavType.IntType; defaultValue = -1 }
                    )
                ) { backStackEntry ->
                    val type = backStackEntry.arguments?.get(TYPE_ARG) as SettingType
                    val id = backStackEntry.arguments?.getInt(ID_ARG) ?: -1

                    SettingDetail(
                        viewModel = settingViewModel,
                        id = id,
                        type = type,
                        onUpPressed = { navController.navigateUp() },
                        onSaved = { navController.navigateUp() }
                    )
                }
                composable(
                    route = HISTORY_DETAIL_ROUTE,
                    arguments = listOf(
                        navArgument(TYPE_ARG) { type = NavType.EnumType(Type::class.java) },
                        navArgument(ID_ARG) { type = NavType.IntType; defaultValue = -1 }
                    )
                ) { backStackEntry ->
                    val type = backStackEntry.arguments?.get(TYPE_ARG) as Type
                    val id = backStackEntry.arguments?.getInt(ID_ARG) ?: -1

                    HistoryDetail(
                        type = type,
                        id = id,
                        onSettingAddClick = { navController.navigateToAddEditSetting(it) },
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
        )
        launchSingleTop = true
        restoreState = true
    }

fun NavHostController.navigateToAddEditHistory(type: Type, id: Int? = -1) {
    this.navigate(
        "$HISTORY_SCREEN/$type".let {
            if (id != -1) "$it?${ID_ARG}=$id" else it
        }
    )
}

fun NavHostController.navigateToAddEditSetting(type: SettingType, id: Int? = -1) {
    this.navigate(
        "$SETTING_SCREEN/$type".let {
            if (id != -1) "$it?${ID_ARG}=$id" else it
        }
    )
}