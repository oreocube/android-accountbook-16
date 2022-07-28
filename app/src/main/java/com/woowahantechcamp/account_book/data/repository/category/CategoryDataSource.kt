package com.woowahantechcamp.account_book.data.repository.category

import android.content.ContentValues
import com.woowahantechcamp.account_book.data.entity.CategoryEntity
import com.woowahantechcamp.account_book.data.repository.AccountBookDBHelper
import com.woowahantechcamp.account_book.data.repository.CategoryEntry
import com.woowahantechcamp.account_book.util.Result
import com.woowahantechcamp.account_book.util.query
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CategoryDataSource @Inject constructor(
    private val dbHelper: AccountBookDBHelper,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun getAllCategory(): Result<List<CategoryEntity>> = withContext(ioDispatcher) {
        try {
            dbHelper.readableDatabase.use { db ->
                val projection = arrayOf(
                    CategoryEntry.COLUMN_NAME_TYPE,
                    CategoryEntry.COLUMN_NAME_TITLE,
                    CategoryEntry.COLUMN_NAME_COLOR
                )

                val cursor = db.query(
                    tableName = CategoryEntry.TABLE_NAME,
                    projection = projection
                )

                val items = mutableListOf<CategoryEntity>()

                with(cursor) {
                    while (moveToNext()) {
                        val type = getInt(getColumnIndexOrThrow(CategoryEntry.COLUMN_NAME_TYPE))
                        val title =
                            getString(getColumnIndexOrThrow(CategoryEntry.COLUMN_NAME_TITLE))
                        val color =
                            getString(getColumnIndexOrThrow(CategoryEntry.COLUMN_NAME_COLOR))

                        items.add(CategoryEntity(type, title, color))
                    }
                }
                cursor.close()
                Result.Success(data = items)
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "fail")
        }
    }

    suspend fun insertCategory(item: CategoryEntity): Result<Long> = withContext(ioDispatcher) {
        try {
            dbHelper.writableDatabase.use { db ->
                val values = ContentValues().apply {
                    put(CategoryEntry.COLUMN_NAME_TYPE, item.type)
                    put(CategoryEntry.COLUMN_NAME_TITLE, item.title)
                    put(CategoryEntry.COLUMN_NAME_COLOR, item.color)
                }

                val newRowId = db.insert(CategoryEntry.TABLE_NAME, null, values)

                Result.Success(data = newRowId)
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "fail")
        }
    }
}