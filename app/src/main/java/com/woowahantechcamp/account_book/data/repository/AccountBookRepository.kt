package com.woowahantechcamp.account_book.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import com.woowahantechcamp.account_book.ui.model.setting.HistoryModel
import com.woowahantechcamp.account_book.ui.model.setting.SettingItem
import com.woowahantechcamp.account_book.ui.model.setting.Type
import com.woowahantechcamp.account_book.util.Result
import java.time.YearMonth
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

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getAllHistories(year: Int, month: Int): Result<List<HistoryModel>> {
        val startDate = YearMonth.of(year, month).atDay(1).toString()
        val endDate = YearMonth.of(year, month).atEndOfMonth().toString()

        return try {

            val result = dataSource.getAllHistory(startDate, endDate)

            Result.Success(data = result.map {
                HistoryModel(
                    id = it.id,
                    date = it.date,
                    type = if (it.type == 1) Type.INCOME else Type.EXPENSES,
                    content = it.content,
                    amount = it.amount,
                    paymentId = it.paymentId,
                    payment = it.paymentTitle,
                    categoryId = it.categoryId,
                    category = it.categoryTitle,
                    color = it.color
                )
            })
        } catch (e: Exception) {
            Result.Error(e.message ?: "exception occur")
        }
    }
}