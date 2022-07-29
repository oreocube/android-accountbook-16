package com.woowahantechcamp.account_book.data.entity

data class HistoryEntity(
    val id: Int,
    val date: String,
    val type: Int,
    val content: String?,
    val amount: Int,
    val paymentEntity: PaymentEntity,
    val categoryEntity: CategoryEntity
)