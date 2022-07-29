package com.woowahantechcamp.account_book.ui.model.setting

enum class Type {
    INCOME,
    EXPENSES
}

data class HistoryModel(
    val date: String,
    val type: Type,
    val content: String?,
    val amount: Int,
    val payment: String,
    val category: String,
    val color: String
)