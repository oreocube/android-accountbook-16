package com.woowahantechcamp.account_book.util

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

fun SQLiteDatabase.query(
    tableName: String,
    projection: Array<String>,
    selection: String? = null,
    selectionArgs: Array<String>? = null,
    order: String = BaseColumns._ID
): Cursor = this.query(tableName, projection, selection, selectionArgs, null, null, order)
