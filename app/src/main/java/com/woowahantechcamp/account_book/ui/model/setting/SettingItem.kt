package com.woowahantechcamp.account_book.ui.model.setting

import androidx.compose.ui.graphics.Color

sealed class SettingItem(
    open val title: String
) {
    data class TextItem(
        override val title: String
    ) : SettingItem(title)

    data class CategoryItem(
        override val title: String,
        val type: Type,
        val color: Color
    ) : SettingItem(title)
}