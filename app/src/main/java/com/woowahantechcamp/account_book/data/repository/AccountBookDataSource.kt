package com.woowahantechcamp.account_book.data.repository

import android.content.ContentValues
import android.provider.BaseColumns
import androidx.core.database.getIntOrNull
import androidx.core.database.getStringOrNull
import com.woowahantechcamp.account_book.data.*
import com.woowahantechcamp.account_book.data.entity.*
import com.woowahantechcamp.account_book.util.query
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AccountBookDataSource @Inject constructor(
    private val dbHelper: AccountBookDBHelper,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun getAllCategory(): List<CategoryEntity> = withContext(ioDispatcher) {
        dbHelper.readableDatabase.run {
            val projection = arrayOf(
                BaseColumns._ID,
                CategoryEntry.COLUMN_NAME_TYPE,
                CategoryEntry.COLUMN_NAME_TITLE,
                CategoryEntry.COLUMN_NAME_COLOR
            )

            val cursor = query(
                tableName = CategoryEntry.TABLE_NAME,
                projection = projection
            )

            val items = mutableListOf<CategoryEntity>()

            with(cursor) {
                while (moveToNext()) {
                    val id = getInt(getColumnIndexOrThrow(BaseColumns._ID))
                    val type = getInt(getColumnIndexOrThrow(CategoryEntry.COLUMN_NAME_TYPE))
                    val title =
                        getString(getColumnIndexOrThrow(CategoryEntry.COLUMN_NAME_TITLE))
                    val color =
                        getInt(getColumnIndexOrThrow(CategoryEntry.COLUMN_NAME_COLOR))

                    items.add(CategoryEntity(id, type, title, color))
                }
            }
            cursor.close()

            items
        }
    }

    suspend fun insertCategory(type: Int, title: String, color: Int): Long =
        withContext(ioDispatcher) {
            dbHelper.writableDatabase.run {
                val values = ContentValues().apply {
                    put(CategoryEntry.COLUMN_NAME_TYPE, type)
                    put(CategoryEntry.COLUMN_NAME_TITLE, title)
                    put(CategoryEntry.COLUMN_NAME_COLOR, color)
                }

                insert(CategoryEntry.TABLE_NAME, null, values)
            }
        }

    suspend fun updateCategory(
        categoryId: Int,
        title: String?,
        color: Int?
    ): Int = withContext(ioDispatcher) {
        dbHelper.writableDatabase.run {
            val values = ContentValues().apply {
                title?.let { put(CategoryEntry.COLUMN_NAME_TITLE, title) }
                color?.let { put(CategoryEntry.COLUMN_NAME_COLOR, color) }
            }

            val selection = "${BaseColumns._ID} = ?"
            val selectionArgs = arrayOf("$categoryId")

            update(
                CategoryEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs
            )
        }
    }

    suspend fun deleteCategory(
        categoryId: Int
    ): Int = withContext(ioDispatcher) {
        dbHelper.writableDatabase.run {
            val selection = "${BaseColumns._ID} = ?"
            val selectionArgs = arrayOf("$categoryId")

            delete(
                CategoryEntry.TABLE_NAME,
                selection,
                selectionArgs
            )
        }
    }

    suspend fun getAllPaymentMethod(): List<PaymentEntity> = withContext(ioDispatcher) {
        dbHelper.readableDatabase.run {
            val projection = arrayOf(
                BaseColumns._ID,
                PaymentEntry.COLUMN_NAME_TITLE
            )

            val cursor = query(
                tableName = PaymentEntry.TABLE_NAME,
                projection = projection
            )

            val items = mutableListOf<PaymentEntity>()

            with(cursor) {
                while (moveToNext()) {
                    val id = getInt(getColumnIndexOrThrow(BaseColumns._ID))
                    val title = getString(getColumnIndexOrThrow(PaymentEntry.COLUMN_NAME_TITLE))

                    items.add(PaymentEntity(id, title))
                }
            }
            cursor.close()

            items
        }
    }

    suspend fun insertPaymentMethod(title: String): Long =
        withContext(ioDispatcher) {
            dbHelper.writableDatabase.run {
                val values = ContentValues().apply {
                    put(PaymentEntry.COLUMN_NAME_TITLE, title)
                }

                insert(PaymentEntry.TABLE_NAME, null, values)
            }
        }

    suspend fun updatePaymentMethod(
        paymentId: Int,
        title: String?
    ): Int = withContext(ioDispatcher) {
        dbHelper.writableDatabase.run {
            val values = ContentValues().apply {
                title?.let { put(PaymentEntry.COLUMN_NAME_TITLE, title) }
            }

            val selection = "${BaseColumns._ID} = ?"
            val selectionArgs = arrayOf("$paymentId")

            update(
                PaymentEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs
            )
        }
    }

    suspend fun deletePaymentMethod(
        paymentId: Int
    ): Int = withContext(ioDispatcher) {
        dbHelper.writableDatabase.run {
            val selection = "${BaseColumns._ID} = ?"
            val selectionArgs = arrayOf("$paymentId")

            delete(
                PaymentEntry.TABLE_NAME,
                selection,
                selectionArgs
            )
        }
    }

    suspend fun getHistoryById(id: Int): HistoryEntity = withContext(ioDispatcher) {
        dbHelper.readableDatabase.run {
            val cursor = rawQuery(SQL_GET_HISTORY_BY_ID, arrayOf("$id"))

            lateinit var item: HistoryEntity
            with(cursor) {
                while (moveToNext()) {
                    item = HistoryEntity(
                        id = getInt(getColumnIndexOrThrow(BaseColumns._ID)),
                        date = getString(getColumnIndexOrThrow(HistoryEntry.COLUMN_NAME_DATE)),
                        type = getInt(getColumnIndexOrThrow(HistoryEntry.COLUMN_NAME_TYPE)),
                        content = getString(getColumnIndexOrThrow(HistoryEntry.COLUMN_NAME_CONTENT)),
                        amount = getInt(getColumnIndexOrThrow(HistoryEntry.COLUMN_NAME_AMOUNT)),
                        paymentId = getIntOrNull(getColumnIndexOrThrow(HistoryEntry.COLUMN_NAME_PAYMENT_ID)),
                        paymentTitle = getStringOrNull(getColumnIndexOrThrow(PaymentEntry.COLUMN_NAME_TITLE)),
                        categoryId = getInt(getColumnIndexOrThrow(HistoryEntry.COLUMN_NAME_PAYMENT_ID)),
                        categoryTitle = getString(getColumnIndexOrThrow(CategoryEntry.COLUMN_NAME_TITLE)),
                        color = getInt(getColumnIndexOrThrow(CategoryEntry.COLUMN_NAME_COLOR))
                    )
                }
            }
            cursor.close()

            item
        }
    }

    suspend fun getAllHistory(startDate: String, endDate: String): List<HistoryEntity> =
        withContext(ioDispatcher) {
            dbHelper.readableDatabase.run {
                val cursor = rawQuery(SQL_SELECT_ALL_HISTORIES, arrayOf(startDate, endDate))

                val items = mutableListOf<HistoryEntity>()
                with(cursor) {
                    while (moveToNext()) {
                        items.add(
                            HistoryEntity(
                                id = getInt(getColumnIndexOrThrow(BaseColumns._ID)),
                                date = getString(getColumnIndexOrThrow(HistoryEntry.COLUMN_NAME_DATE)),
                                type = getInt(getColumnIndexOrThrow(HistoryEntry.COLUMN_NAME_TYPE)),
                                content = getString(getColumnIndexOrThrow(HistoryEntry.COLUMN_NAME_CONTENT)),
                                amount = getInt(getColumnIndexOrThrow(HistoryEntry.COLUMN_NAME_AMOUNT)),
                                paymentId = getIntOrNull(getColumnIndexOrThrow(HistoryEntry.COLUMN_NAME_PAYMENT_ID)),
                                paymentTitle = getStringOrNull(getColumnIndexOrThrow(PaymentEntry.COLUMN_NAME_TITLE)),
                                categoryId = getInt(getColumnIndexOrThrow(HistoryEntry.COLUMN_NAME_PAYMENT_ID)),
                                categoryTitle = getString(getColumnIndexOrThrow(CategoryEntry.COLUMN_NAME_TITLE)),
                                color = getInt(getColumnIndexOrThrow(CategoryEntry.COLUMN_NAME_COLOR))
                            )
                        )
                    }
                }
                cursor.close()

                items
            }
        }

    suspend fun insertHistoryItem(
        type: Int,
        date: String,
        amount: Int,
        paymentId: Int,
        categoryId: Int,
        content: String?
    ) = withContext(ioDispatcher) {
        dbHelper.writableDatabase.run {
            execSQL(SQL_SET_PRAGMA)

            val values = ContentValues().apply {
                put(HistoryEntry.COLUMN_NAME_TYPE, type)
                put(HistoryEntry.COLUMN_NAME_DATE, date)
                put(HistoryEntry.COLUMN_NAME_AMOUNT, amount)
                if (type == 2) {
                    put(HistoryEntry.COLUMN_NAME_PAYMENT_ID, paymentId)
                }
                put(HistoryEntry.COLUMN_NAME_CATEGORY_ID, categoryId)
                put(HistoryEntry.COLUMN_NAME_CONTENT, content)
            }

            insert(HistoryEntry.TABLE_NAME, null, values)
        }
    }

    suspend fun updateHistory(
        id: Int,
        date: String?,
        type: Int?,
        amount: Int?,
        content: String?,
        paymentId: Int?,
        categoryId: Int?
    ) = withContext(ioDispatcher) {
        dbHelper.writableDatabase.run {
            execSQL(SQL_SET_PRAGMA)

            val values = ContentValues().apply {
                date?.let { put(HistoryEntry.COLUMN_NAME_DATE, date) }
                type?.let { put(HistoryEntry.COLUMN_NAME_TYPE, type) }
                amount?.let { put(HistoryEntry.COLUMN_NAME_AMOUNT, amount) }
                content?.let { put(HistoryEntry.COLUMN_NAME_CONTENT, content) }
                paymentId?.let { put(HistoryEntry.COLUMN_NAME_PAYMENT_ID, paymentId) }
                categoryId?.let { put(HistoryEntry.COLUMN_NAME_CATEGORY_ID, categoryId) }
            }

            val selection = "${BaseColumns._ID} = ?"
            val selectionArgs = arrayOf("$id")

            update(
                HistoryEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs
            )
        }
    }

    suspend fun deleteHistoryItems(
        list: List<Int>
    ): Int = withContext(ioDispatcher) {
        dbHelper.writableDatabase.run {
            var count = 0
            list.forEach {
                val selection = "${BaseColumns._ID} = ?"
                val selectionArgs = arrayOf("$it")

                count += delete(
                    HistoryEntry.TABLE_NAME,
                    selection,
                    selectionArgs
                )
            }
            count
        }
    }

    suspend fun getSumOfExpenseCategory(startDate: String, endDate: String) =
        withContext(ioDispatcher) {
            dbHelper.readableDatabase.run {
                execSQL(SQL_SET_PRAGMA)

                val cursor = rawQuery(SQL_SELECT_GROUP_BY_CATEGORY, arrayOf(startDate, endDate))

                val items = mutableListOf<StatisticEntity>()
                with(cursor) {
                    while (moveToNext()) {
                        val categoryId = getInt(0)
                        val categoryTitle = getString(1)
                        val color = getInt(2)
                        val sum = getLong(3)

                        items.add(StatisticEntity(categoryId, categoryTitle, color, sum))
                    }
                }
                cursor.close()

                items
            }
        }

    suspend fun getSumOfIncomeAndExpenseForDate(startDate: String, endDate: String) =
        withContext(ioDispatcher) {
            dbHelper.readableDatabase.run {
                val cursor = rawQuery(SQL_GET_SUM_GROUP_BY_DATE, arrayOf(startDate, endDate))

                val items = mutableListOf<CalendarEntity>()

                with(cursor) {
                    while (moveToNext()) {
                        val date = getString(0)
                        val value = getLong(1)
                        val type = getInt(2)

                        items.add(CalendarEntity(date, value, type))
                    }
                }
                cursor.close()

                items
            }
        }
}