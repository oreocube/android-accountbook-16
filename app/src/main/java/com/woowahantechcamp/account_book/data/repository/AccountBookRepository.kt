package com.woowahantechcamp.account_book.data.repository

import com.woowahantechcamp.account_book.data.entity.CategoryEntity
import javax.inject.Inject

class AccountBookRepository @Inject constructor(
    private val categoryDataSource: AccountBookDataSource
) {
    suspend fun addCategory(type: Int, title: String, color: String) {
        categoryDataSource.insertCategory(CategoryEntity(type, title, color))
    }
}