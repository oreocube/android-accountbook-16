package com.woowahantechcamp.account_book.data.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class AccountBookDBHelper(
    context: Context
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private val historyTableCreateSQL = with(HistoryEntry) {
        "CREATE TABLE $TABLE_NAME (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY, " +
                "$COLUMN_NAME_TYPE INTEGER NOT NULL, " +
                "$COLUMN_NAME_DATE DATE NOT NULL, " +
                "$COLUMN_NAME_AMOUNT INTEGER NOT NULL, " +
                "$COLUMN_NAME_PAYMENT_ID INTEGER NOT NULL, " +
                "$COLUMN_NAME_CATEGORY_ID INTEGER NOT NULL, " +
                "$COLUMN_NAME_CONTENT TEXT, " +
                "FOREIGN KEY (${COLUMN_NAME_PAYMENT_ID}) " +
                "REFERENCES ${PaymentEntry.TABLE_NAME} (${BaseColumns._ID}), " +
                "FOREIGN KEY (${COLUMN_NAME_CATEGORY_ID}) " +
                "REFERENCES ${CategoryEntry.TABLE_NAME} (${BaseColumns._ID}))"
    }

    private val paymentTableCreateSQL = with(PaymentEntry) {
        "CREATE TABLE $TABLE_NAME (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY, " +
                "$COLUMN_NAME_TITLE TEXT NOT NULL)"
    }

    private val categoryTableCreateSQL = with(CategoryEntry) {
        "CREATE TABLE $TABLE_NAME (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY, " +
                "$COLUMN_NAME_TYPE INTEGER NOT NULL, " +
                "$COLUMN_NAME_TITLE TEXT NOT NULL, " +
                "$COLUMN_NAME_COLOR TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(paymentTableCreateSQL)
        db.execSQL(categoryTableCreateSQL)
        db.execSQL(historyTableCreateSQL)
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