package com.woowahantechcamp.account_book.data.repository

import android.content.ContentValues
import com.woowahantechcamp.account_book.data.entity.CategoryEntity
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
                    val type = getInt(getColumnIndexOrThrow(CategoryEntry.COLUMN_NAME_TYPE))
                    val title =
                        getString(getColumnIndexOrThrow(CategoryEntry.COLUMN_NAME_TITLE))
                    val color =
                        getString(getColumnIndexOrThrow(CategoryEntry.COLUMN_NAME_COLOR))

                    items.add(CategoryEntity(type, title, color))
                }
            }
            cursor.close()

            items
        }
    }

    suspend fun insertCategory(item: CategoryEntity): Long = withContext(ioDispatcher) {
        dbHelper.writableDatabase.run {
            val values = ContentValues().apply {
                put(CategoryEntry.COLUMN_NAME_TYPE, item.type)
                put(CategoryEntry.COLUMN_NAME_TITLE, item.title)
                put(CategoryEntry.COLUMN_NAME_COLOR, item.color)
            }

            insert(CategoryEntry.TABLE_NAME, null, values)
        }
    }
}