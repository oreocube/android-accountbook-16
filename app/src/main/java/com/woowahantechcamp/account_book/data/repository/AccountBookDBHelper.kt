package com.woowahantechcamp.account_book.data.repository

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.woowahantechcamp.account_book.data.SQL_CREATE_CATEGORY_TABLE
import com.woowahantechcamp.account_book.data.SQL_CREATE_HISTORY_TABLE
import com.woowahantechcamp.account_book.data.SQL_CREATE_PAYMENT_TABLE
import com.woowahantechcamp.account_book.data.SQL_SET_PRAGMA
import com.woowahantechcamp.account_book.data.entity.CategoryEntity

class AccountBookDBHelper(
    context: Context
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_SET_PRAGMA)
        db.execSQL(SQL_CREATE_PAYMENT_TABLE)
        db.execSQL(SQL_CREATE_CATEGORY_TABLE)
        db.execSQL(SQL_CREATE_HISTORY_TABLE)

        initialData.forEach {
            val values = ContentValues().apply {
                put(BaseColumns._ID, it.categoryId)
                put(CategoryEntry.COLUMN_NAME_TYPE, it.type)
                put(CategoryEntry.COLUMN_NAME_TITLE, it.title)
                put(CategoryEntry.COLUMN_NAME_COLOR, it.color)
            }

            db.insert(CategoryEntry.TABLE_NAME, null, values)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // TODO
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // TODO
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "AccountBook.db"

        val initialData = listOf(
            CategoryEntity(0, 1, "월급", "#9BD182"),
            CategoryEntity(1, 1, "용돈", "#EDCF65"),
            CategoryEntity(2, 1, "기타", "#E29C4D"),
            CategoryEntity(3, 2, "교통", "#94D3CC"),
            CategoryEntity(4, 2, "문화/여가", "#D092E2"),
            CategoryEntity(5, 2, "미분류", "#817DCE"),
            CategoryEntity(6, 2, "생활", "#4A6CC3"),
            CategoryEntity(7, 2, "쇼핑/뷰티", "#4CB8B8"),
            CategoryEntity(8, 2, "식비", "#4CA1DE"),
            CategoryEntity(9, 2, "의료/건강", "#6ED5EB")
        )
    }
}