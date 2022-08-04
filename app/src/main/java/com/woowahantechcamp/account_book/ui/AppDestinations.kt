package com.woowahantechcamp.account_book.ui

import com.woowahantechcamp.account_book.ui.DestinationsArgs.ID_ARG
import com.woowahantechcamp.account_book.ui.DestinationsArgs.TYPE_ARG
import com.woowahantechcamp.account_book.ui.Screens.HISTORY_SCREEN
import com.woowahantechcamp.account_book.ui.Screens.SETTING_SCREEN

object Screens {
    const val HISTORY_SCREEN = "history"
    const val CALENDAR_SCREEN = "calendar"
    const val GRAPH_SCREEN = "graph"
    const val SETTING_SCREEN = "setting"
}

object DestinationsArgs{
    const val TYPE_ARG = "type"
    const val ID_ARG = "id"
}

object MainDestinations {
    const val HISTORY_DETAIL_ROUTE = "$HISTORY_SCREEN/{$TYPE_ARG}?$ID_ARG={$ID_ARG}"
    const val SETTING_DETAIL_ROUTE = "$SETTING_SCREEN/{$TYPE_ARG}?$ID_ARG={$ID_ARG}"
}