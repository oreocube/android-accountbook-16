package com.woowahantechcamp.account_book.util

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
val now = LocalDate.now()

val stringToDateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
val dateToStringFormatter = SimpleDateFormat("M월 d일 E", Locale.KOREA)

@SuppressLint("SimpleDateFormat")
fun String.toDate(): Date = stringToDateFormatter.parse(this)

fun Date.toFormattedDateString(): String = dateToStringFormatter.format(this)

@RequiresApi(Build.VERSION_CODES.O)
fun String.toLocalDate(): LocalDate? {
    val year = this.substring(0,4).toInt()
    val month = this.substring(5,7).toInt()
    val dayOfMonth = this.substring(8).toInt()

    return LocalDate.of(year, month, dayOfMonth)
}
