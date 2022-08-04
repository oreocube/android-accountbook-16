package com.woowahantechcamp.account_book.data.entity

data class StatisticEntity(
    val categoryId: Int,
    val categoryTitle: String,
    val color: Int,
    val sum: Long
)