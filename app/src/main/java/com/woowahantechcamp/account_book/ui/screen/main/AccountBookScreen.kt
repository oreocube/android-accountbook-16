package com.woowahantechcamp.account_book.ui.screen.main

import com.woowahantechcamp.account_book.R

sealed class AccountBookScreen(
    val route: String,
    val title: Int,
    val icon: Int
) {
    object History : AccountBookScreen(
        route = "history",
        title = R.string.bottom_navigation_history,
        icon = R.drawable.ic_document_list
    )

    object Calendar : AccountBookScreen(
        route = "calendar",
        title = R.string.bottom_navigation_calendar,
        icon = R.drawable.ic_calendar_month
    )

    object Graph : AccountBookScreen(
        route = "graph",
        title = R.string.bottom_navigation_graph,
        icon = R.drawable.ic_graph
    )

    object Setting : AccountBookScreen(
        route = "setting",
        title = R.string.bottom_navigation_setting,
        icon = R.drawable.ic_settings
    )

}