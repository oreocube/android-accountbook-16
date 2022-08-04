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

    fun fetchData(year: Int, month: Int, onFailure: (String) -> Unit) {
        viewModelScope.launch {
            when (val result = repository.getCalendarData(year, month)) {
                is Result.Success -> {
                    _calendarData.value = result.data
                }
                is Result.Error -> {
                    onFailure("데이터를 불러오지 못했습니다.")
                }
            }
        }
    }

}