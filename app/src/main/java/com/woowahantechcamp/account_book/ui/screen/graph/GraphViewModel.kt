package com.woowahantechcamp.account_book.ui.screen.graph

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowahantechcamp.account_book.data.repository.AccountBookRepository
import com.woowahantechcamp.account_book.ui.model.StatisticModel
import com.woowahantechcamp.account_book.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class GraphViewModel @Inject constructor(
    private val repository: AccountBookRepository
) : ViewModel() {
    private val _statistics = mutableStateOf<List<StatisticModel>>(listOf())
    val statistics: State<List<StatisticModel>> = _statistics

    fun fetchData(year: Int, month: Int) {
        viewModelScope.launch {
            val result = repository.getSumOfExpense(year, month)

            if (result is Result.Success) {
                _statistics.value = result.data
            }
        }
    }
}