package com.woowahantechcamp.account_book.ui.model.setting

enum class Type(val title: String) {
    INCOME("수입"),
    EXPENSES("지출")
}

data class HistoryModel(
    val id: Int,
    val date: String,
    val type: Type,
    val content: String?,
    val amount: Int,
    val paymentId: Int,
    val payment: String,
    val categoryId: Int,
    val category: String,
    val color: String
)