package com.woowahantechcamp.account_book.data.repository

import javax.inject.Inject

class AccountBookRepository @Inject constructor(
    private val categoryDataSource: AccountBookDataSource
) {
    suspend fun addCategory(type: Int, title: String, color: String) {
        categoryDataSource.insertCategory(type, title, color)
    }
}