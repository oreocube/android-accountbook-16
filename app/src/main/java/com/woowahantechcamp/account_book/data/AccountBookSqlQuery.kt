package com.woowahantechcamp.account_book.data

import android.provider.BaseColumns
import com.woowahantechcamp.account_book.data.repository.CategoryEntry
import com.woowahantechcamp.account_book.data.repository.HistoryEntry
import com.woowahantechcamp.account_book.data.repository.PaymentEntry

const val SQL_CREATE_HISTORY_TABLE =
    "CREATE TABLE ${HistoryEntry.TABLE_NAME} (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "${HistoryEntry.COLUMN_NAME_TYPE} INTEGER NOT NULL, " +
            "${HistoryEntry.COLUMN_NAME_DATE} DATE NOT NULL, " +
            "${HistoryEntry.COLUMN_NAME_AMOUNT} INTEGER NOT NULL, " +
            "${HistoryEntry.COLUMN_NAME_PAYMENT_ID} INTEGER, " +
            "${HistoryEntry.COLUMN_NAME_CATEGORY_ID} INTEGER NOT NULL, " +
            "${HistoryEntry.COLUMN_NAME_CONTENT} TEXT, " +
            "FOREIGN KEY (${HistoryEntry.COLUMN_NAME_PAYMENT_ID}) " +
            "REFERENCES ${PaymentEntry.TABLE_NAME} (${BaseColumns._ID}), " +
            "FOREIGN KEY (${HistoryEntry.COLUMN_NAME_CATEGORY_ID}) " +
            "REFERENCES ${CategoryEntry.TABLE_NAME} (${BaseColumns._ID}))"

const val SQL_CREATE_PAYMENT_TABLE =
    "CREATE TABLE ${PaymentEntry.TABLE_NAME} (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "${PaymentEntry.COLUMN_NAME_TITLE} TEXT NOT NULL)"

const val SQL_CREATE_CATEGORY_TABLE =
    "CREATE TABLE ${CategoryEntry.TABLE_NAME} (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "${CategoryEntry.COLUMN_NAME_TYPE} INTEGER NOT NULL, " +
            "${CategoryEntry.COLUMN_NAME_TITLE} TEXT NOT NULL, " +
            "${CategoryEntry.COLUMN_NAME_COLOR} INTEGER NOT NULL)"

const val SQL_SET_PRAGMA = "PRAGMA foreign_keys = ON"

const val SQL_DROP_HISTORY_TABLE = "DROP TABLE IF EXISTS ${HistoryEntry.TABLE_NAME}"
const val SQL_DROP_CATEGORY_TABLE = "DROP TABLE IF EXISTS ${CategoryEntry.TABLE_NAME}"
const val SQL_DROP_PAYMENT_TABLE = "DROP TABLE IF EXISTS ${PaymentEntry.TABLE_NAME}"

const val SQL_GET_HISTORY_BY_ID =
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
            "FROM ${HistoryEntry.TABLE_NAME} " +
            "LEFT JOIN ${PaymentEntry.TABLE_NAME} " +
            "ON ${HistoryEntry.TABLE_NAME}.${HistoryEntry.COLUMN_NAME_PAYMENT_ID} " +
            "= ${PaymentEntry.TABLE_NAME}.${BaseColumns._ID} " +
            "LEFT JOIN ${CategoryEntry.TABLE_NAME} " +
            "ON ${HistoryEntry.TABLE_NAME}.${HistoryEntry.COLUMN_NAME_CATEGORY_ID} " +
            "= ${CategoryEntry.TABLE_NAME}.${BaseColumns._ID} " +
            "WHERE ${HistoryEntry.TABLE_NAME}.${BaseColumns._ID} = ?"

const val SQL_SELECT_ALL_HISTORIES =
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
            "FROM ${HistoryEntry.TABLE_NAME} " +
            "LEFT JOIN ${PaymentEntry.TABLE_NAME} " +
            "ON ${HistoryEntry.TABLE_NAME}.${HistoryEntry.COLUMN_NAME_PAYMENT_ID} " +
            "= ${PaymentEntry.TABLE_NAME}.${BaseColumns._ID} " +
            "LEFT JOIN ${CategoryEntry.TABLE_NAME} " +
            "ON ${HistoryEntry.TABLE_NAME}.${HistoryEntry.COLUMN_NAME_CATEGORY_ID} " +
            "= ${CategoryEntry.TABLE_NAME}.${BaseColumns._ID} " +
            "WHERE ${HistoryEntry.COLUMN_NAME_DATE} BETWEEN ? AND ? " +
            "ORDER BY ${HistoryEntry.COLUMN_NAME_DATE} DESC"

const val SQL_SELECT_GROUP_BY_CATEGORY =
    "SELECT ${HistoryEntry.TABLE_NAME}.${HistoryEntry.COLUMN_NAME_CATEGORY_ID}, " +
            "${CategoryEntry.TABLE_NAME}.${CategoryEntry.COLUMN_NAME_TITLE}, " +
            "${CategoryEntry.TABLE_NAME}.${CategoryEntry.COLUMN_NAME_COLOR}, " +
            "SUM(${HistoryEntry.TABLE_NAME}.${HistoryEntry.COLUMN_NAME_AMOUNT}) AS SUM " +
            "FROM ${HistoryEntry.TABLE_NAME} " +
            "LEFT JOIN ${CategoryEntry.TABLE_NAME} " +
            "ON ${HistoryEntry.TABLE_NAME}.${HistoryEntry.COLUMN_NAME_CATEGORY_ID} " +
            "= ${CategoryEntry.TABLE_NAME}.${BaseColumns._ID} " +
            "WHERE ${HistoryEntry.TABLE_NAME}.${CategoryEntry.COLUMN_NAME_TYPE} = 2 " +
            "AND ${HistoryEntry.COLUMN_NAME_DATE} BETWEEN ? AND ? " +
            "GROUP BY ${HistoryEntry.TABLE_NAME}.${HistoryEntry.COLUMN_NAME_CATEGORY_ID} " +
            "ORDER BY SUM DESC"

const val SQL_GET_SUM_GROUP_BY_DATE =
    "SELECT ${HistoryEntry.COLUMN_NAME_DATE}, SUM(${HistoryEntry.COLUMN_NAME_AMOUNT}), ${HistoryEntry.COLUMN_NAME_TYPE} " +
            "FROM ${HistoryEntry.TABLE_NAME} " +
            "WHERE ${HistoryEntry.COLUMN_NAME_DATE} BETWEEN date(?) AND date(?) " +
            "GROUP BY ${HistoryEntry.COLUMN_NAME_TYPE}, ${HistoryEntry.COLUMN_NAME_DATE} " +
            "ORDER BY ${HistoryEntry.COLUMN_NAME_DATE} "