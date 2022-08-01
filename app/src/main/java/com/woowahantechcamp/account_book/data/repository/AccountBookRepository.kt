package com.woowahantechcamp.account_book.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import com.woowahantechcamp.account_book.ui.model.CategoryModel
import com.woowahantechcamp.account_book.ui.model.HistoryModel
import com.woowahantechcamp.account_book.ui.model.PaymentModel
import com.woowahantechcamp.account_book.ui.model.Type
import com.woowahantechcamp.account_book.ui.screen.setting.SettingType
import com.woowahantechcamp.account_book.util.Result
import java.time.YearMonth
import javax.inject.Inject

class AccountBookRepository @Inject constructor(
    private val dataSource: AccountBookDataSource
) {
    suspend fun addCategory(type: SettingType, title: String, color: Long): Result<Long> {
        return try {
            val typeInt = if (type == SettingType.INCOME) 1 else 2
            val result = dataSource.insertCategory(typeInt, title, color)

            Result.Success(result)
        } catch (e: Exception) {
            Result.Error(e.message ?: "exception occur")
        }
    }

    suspend fun getAllCategories(): Result<List<CategoryModel>> {
        return try {
            val result = dataSource.getAllCategory()

            Result.Success(data = result.map {
                CategoryModel(
                    id = it.categoryId,
                    type = if (it.type == 1) Type.INCOME else Type.EXPENSES,
                    title = it.title,
                    color = Color(it.color)
                )
            })
        } catch (e: Exception) {
            Result.Error(e.message ?: "exception occur")
        }
    }

    suspend fun updateCategory(id: Int, title: String, color: Long): Result<Int> {
        return try {
            val result = dataSource.updateCategory(id, title, color)

            Result.Success(result)
        } catch (e: Exception) {
            Result.Error(e.message ?: "exception occur")
        }
    }

    suspend fun addPayment(title: String): Result<Long> {
        return try {
            val result = dataSource.insertPaymentMethod(title)

            Result.Success(result)
        } catch (e: Exception) {
            Result.Error(e.message ?: "exception occur")
        }
    }

    suspend fun getAllPayment(): Result<List<PaymentModel>> {
        return try {
            val result = dataSource.getAllPaymentMethod()

            Result.Success(data = result.map {
                PaymentModel(
                    id = it.paymentId,
                    title = it.title
                )
            })
        } catch (e: Exception) {
            Result.Error(e.message ?: "exception occur")
        }
    }

    suspend fun updatePayment(id: Int, title: String): Result<Int> {
        return try {
            val result = dataSource.updatePaymentMethod(id, title)

            Result.Success(result)
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
                    title = it.content ?: "",
                    amount = it.amount,
                    paymentId = it.paymentId,
                    payment = it.paymentTitle,
                    categoryId = it.categoryId,
                    category = it.categoryTitle,
                    color = Color(it.color)
                )
            })
        } catch (e: Exception) {
            Result.Error(e.message ?: "exception occur")
        }
    }
}