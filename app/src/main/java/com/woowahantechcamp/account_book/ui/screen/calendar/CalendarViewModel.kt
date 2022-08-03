package com.woowahantechcamp.account_book.ui.screen.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowahantechcamp.account_book.data.entity.CalendarEntity
import com.woowahantechcamp.account_book.data.repository.AccountBookRepository
import com.woowahantechcamp.account_book.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val repository: AccountBookRepository
) : ViewModel() {
    private val _calendarData = mutableStateOf<List<CalendarEntity>>(listOf())
    val calendarData = _calendarData

    fun fetchData(year: Int, month: Int) {
        viewModelScope.launch {
            val result = repository.getCalendarData(year, month)

            if (result is Result.Success) {
                _calendarData.value = result.data
            }
        }
    }

}