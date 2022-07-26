package com.woowahantechcamp.account_book.data.repository

import android.provider.BaseColumns

object HistoryEntry : BaseColumns {
    const val TABLE_NAME = "history"
    const val COLUMN_NAME_TYPE = "type"
    const val COLUMN_NAME_DATE = "date"
    const val COLUMN_NAME_AMOUNT = "amount"
    const val COLUMN_NAME_PAYMENT_ID = "payment_id"
    const val COLUMN_NAME_CATEGORY_ID = "category_id"
    const val COLUMN_NAME_CONTENT = "content"
}

object PaymentEntry : BaseColumns {
    const val TABLE_NAME = "payment"
    const val COLUMN_NAME_TITLE = "title"
}

object CategoryEntry : BaseColumns {
    const val TABLE_NAME = "category"
    const val COLUMN_NAME_TYPE = "type"
    const val COLUMN_NAME_TITLE = "title"
    const val COLUMN_NAME_COLOR = "color"
}