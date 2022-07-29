package com.woowahantechcamp.account_book.data

import android.provider.BaseColumns
import com.woowahantechcamp.account_book.data.repository.CategoryEntry
import com.woowahantechcamp.account_book.data.repository.HistoryEntry
import com.woowahantechcamp.account_book.data.repository.PaymentEntry

const val SELECT_ALL_HISTORIES =
    "SELECT ${HistoryEntry.TABLE_NAME}.${BaseColumns._ID}, " +
            "${HistoryEntry.TABLE_NAME}.${HistoryEntry.COLUMN_NAME_TYPE}, " +
            "${HistoryEntry.TABLE_NAME}.${HistoryEntry.COLUMN_NAME_DATE}, " +
            "${HistoryEntry.TABLE_NAME}.${HistoryEntry.COLUMN_NAME_AMOUNT}, " +
            "${HistoryEntry.TABLE_NAME}.${HistoryEntry.COLUMN_NAME_CONTENT}, " +
            "${HistoryEntry.TABLE_NAME}.${HistoryEntry.COLUMN_NAME_PAYMENT_ID}, " +
            "${HistoryEntry.TABLE_NAME}.${HistoryEntry.COLUMN_NAME_CATEGORY_ID}, " +
            "${PaymentEntry.TABLE_NAME}.${PaymentEntry.COLUMN_NAME_TITLE}, " +
            "${CategoryEntry.TABLE_NAME}.${CategoryEntry.COLUMN_NAME_TITLE}, " +
            "${CategoryEntry.TABLE_NAME}.${CategoryEntry.COLUMN_NAME_COLOR} " +
            "FROM ${HistoryEntry.TABLE_NAME}, ${PaymentEntry.TABLE_NAME}, ${CategoryEntry.TABLE_NAME}" +
            "WHERE ${HistoryEntry.COLUMN_NAME_PAYMENT_ID} = ${PaymentEntry.TABLE_NAME}.${BaseColumns._ID} " +
            "AND ${HistoryEntry.COLUMN_NAME_CATEGORY_ID} = ${CategoryEntry.TABLE_NAME}.${BaseColumns._ID} " +
            "AND ${HistoryEntry.COLUMN_NAME_DATE} BETWEEN ? AND ?"