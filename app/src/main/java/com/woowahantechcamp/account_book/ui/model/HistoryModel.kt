package com.woowahantechcamp.account_book.ui.model

enum class Type(val title: String) {
    INCOME("수입"),
    EXPENSES("지출")
}

data class HistoryModel(
    override val id: Int,
    override val title: String,
    val date: String,
    val type: Type,
    val amount: Int,
    val paymentId: Int,
    val payment: String,
    val categoryId: Int,
    val category: String,
    val color: String
) : Model