package com.woowahantechcamp.account_book.data.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.woowahantechcamp.account_book.data.SQL_CREATE_CATEGORY_TABLE
import com.woowahantechcamp.account_book.data.SQL_CREATE_HISTORY_TABLE
import com.woowahantechcamp.account_book.data.SQL_CREATE_PAYMENT_TABLE
import com.woowahantechcamp.account_book.data.SQL_SET_PRAGMA

class AccountBookDBHelper(
    context: Context
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_SET_PRAGMA)
        db.execSQL(SQL_CREATE_PAYMENT_TABLE)
        db.execSQL(SQL_CREATE_CATEGORY_TABLE)
        db.execSQL(SQL_CREATE_HISTORY_TABLE)
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
    }
}