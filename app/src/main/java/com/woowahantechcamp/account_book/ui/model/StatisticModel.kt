package com.woowahantechcamp.account_book.ui.model

import androidx.compose.ui.graphics.Color

data class StatisticModel(
    val categoryId : Int,
    val categoryTitle: String,
    val color: Color,
    val sum : Long
)