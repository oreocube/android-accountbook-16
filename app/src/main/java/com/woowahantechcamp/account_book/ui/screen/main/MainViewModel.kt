package com.woowahantechcamp.account_book.ui.screen.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private val today = LocalDate.now()

    private val _currentDate = mutableStateOf(LocalDate.now())
    val currentDate: State<LocalDate> = _currentDate

    fun moveToNextMonth() {
        val nextMonth = _currentDate.value.plusMonths(1)
        if (nextMonth.year < today.year || nextMonth.month <= today.month) {
            _currentDate.value = nextMonth
        }
    }

    fun moveToPrevMonth() {
        val prevMonth = _currentDate.value.minusMonths(1)
        if (prevMonth.year > 2020) {
            _currentDate.value = prevMonth
        }
    }

    fun setDate(year: Int, month: Int) {
        _currentDate.value = LocalDate.of(year, month, 1)
    }
}