package com.woowahantechcamp.account_book.data.repository

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import com.woowahantechcamp.account_book.ui.model.setting.SettingItem
import com.woowahantechcamp.account_book.ui.model.setting.Type
import com.woowahantechcamp.account_book.util.Result
import javax.inject.Inject

class AccountBookRepository @Inject constructor(
    private val dataSource: AccountBookDataSource
) {
    suspend fun addCategory(type: Int, title: String, color: String): Result<Long> {
        return try {
            val result = dataSource.insertCategory(type, title, color)

            Result.Success(result)
        } catch (e: Exception) {
            Result.Error(e.message ?: "exception occur")
        }
    }

    suspend fun getAllCategories(): Result<List<SettingItem.CategoryItem>> {
        return try {
            val result = dataSource.getAllCategory()

            Result.Success(data = result.map {
                SettingItem.CategoryItem(
                    id = it.categoryId,
                    type = if (it.type == 1) Type.INCOME else Type.EXPENSES,
                    title = it.title,
                    color = Color(it.color.toColorInt())
                )
            })
        } catch (e: Exception) {
            Result.Error(e.message ?: "exception occur")
        }
    }
}