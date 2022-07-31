package com.woowahantechcamp.account_book.ui.model.setting

import androidx.compose.ui.graphics.Color

sealed class SettingItem(
    open val id: Int,
    open val title: String
) {
    data class TextItem(
        override val id: Int,
        override val title: String
    ) : SettingItem(id, title)

    data class CategoryItem(
        override val id: Int,
        override val title: String,
        val type: Type,
        val color: Color
    ) : SettingItem(id, title)
}