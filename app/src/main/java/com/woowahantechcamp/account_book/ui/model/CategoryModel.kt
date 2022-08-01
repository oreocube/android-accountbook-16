package com.woowahantechcamp.account_book.ui.model

import androidx.compose.ui.graphics.Color

data class CategoryModel(
    override val id: Int,
    override val title: String,
    val type: Type,
    val color: Color
) : Model