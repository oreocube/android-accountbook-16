package com.woowahantechcamp.account_book.ui.screen.main

import com.woowahantechcamp.account_book.R
import com.woowahantechcamp.account_book.ui.Screens

sealed class AccountBookScreen(
    val route: String,
    val title: Int,
    val icon: Int
) {
    object History : AccountBookScreen(
        route = Screens.HISTORY_SCREEN,
        title = R.string.bottom_navigation_history,
        icon = R.drawable.ic_document_list
    )

    object Calendar : AccountBookScreen(
        route = Screens.CALENDAR_SCREEN,
        title = R.string.bottom_navigation_calendar,
        icon = R.drawable.ic_calendar_month
    )

    object Graph : AccountBookScreen(
        route = Screens.GRAPH_SCREEN,
        title = R.string.bottom_navigation_graph,
        icon = R.drawable.ic_graph
    )

    object Setting : AccountBookScreen(
        route = Screens.SETTING_SCREEN,
        title = R.string.bottom_navigation_setting,
        icon = R.drawable.ic_settings
    )

}