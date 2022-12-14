package com.woowahantechcamp.account_book.data.repository

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.woowahantechcamp.account_book.data.*
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
        if (oldVersion != newVersion) {
            db.execSQL(SQL_DROP_HISTORY_TABLE)
            db.execSQL(SQL_DROP_CATEGORY_TABLE)
            db.execSQL(SQL_DROP_PAYMENT_TABLE)
            onCreate(db)
        }
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "AccountBook.db"

        val initialData = listOf(
            CategoryEntity(0, 1, "월급", 0),
            CategoryEntity(1, 1, "용돈", 5),
            CategoryEntity(2, 1, "기타", 8),
            CategoryEntity(3, 2, "교통", 6),
            CategoryEntity(4, 2, "문화/여가", 14),
            CategoryEntity(5, 2, "미분류", 11),
            CategoryEntity(6, 2, "생활", 0),
            CategoryEntity(7, 2, "쇼핑/뷰티", 7),
            CategoryEntity(8, 2, "식비", 2),
            CategoryEntity(9, 2, "의료/건강", 4)
        )
    }
}