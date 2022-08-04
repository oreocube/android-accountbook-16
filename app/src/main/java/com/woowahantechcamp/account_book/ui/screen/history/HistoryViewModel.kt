package com.woowahantechcamp.account_book.ui.screen.history

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowahantechcamp.account_book.data.repository.AccountBookRepository
import com.woowahantechcamp.account_book.ui.model.HistoryModel
import com.woowahantechcamp.account_book.ui.model.Type
import com.woowahantechcamp.account_book.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: AccountBookRepository
) : ViewModel() {

    private val _year = mutableStateOf(LocalDate.now().year)
    private fun setYear(year: Int) {
        _year.value = year
    }

    private val _month = mutableStateOf(LocalDate.now().monthValue)
    private fun setMonth(month: Int) {
        _month.value = month
    }

    private val _historyAll = mutableStateOf<List<HistoryModel>>(listOf())
    val historyAll: MutableState<List<HistoryModel>> = _historyAll

    private val _sumOfIncome = mutableStateOf(0)
    val sumOfIncome: MutableState<Int>
        get() = _sumOfIncome

    private val _sumOfExpense = mutableStateOf(0)
    val sumOfExpense: MutableState<Int>
        get() = _sumOfExpense

    private val _selectedItems = mutableStateListOf<Int>()
    val selectedItems: MutableList<Int>
        get() = _selectedItems

    fun addSelectedItem(id: Int) {
        _selectedItems.add(id)
    }

    fun removeSelectedItem(id: Int) {
        _selectedItems.remove(id)
    }

    fun clearSelectedItem() {
        _selectedItems.clear()
    }

    fun fetchData(year: Int, month: Int, onFailure: (String) -> Unit) {
        setYear(year)
        setMonth(month)
        viewModelScope.launch {
            when (val result = repository.getAllHistories(year, month)) {
                is Result.Success -> {
                    _historyAll.value = result.data
                    _sumOfIncome.value =
                        result.data.filter { it.type == Type.INCOME }.sumOf { it.amount }
                    _sumOfExpense.value =
                        result.data.filter { it.type == Type.EXPENSES }.sumOf { it.amount }
                }
                is Result.Error -> {
                    onFailure("데이터를 불러오지 못했습니다.")
                }
            }
        }
    }

    fun deleteAllSelectedItems(onFailure: (String) -> Unit) {
        viewModelScope.launch {
            when (repository.deleteHistoryItems(_selectedItems)) {
                is Result.Success -> {
                    _selectedItems.clear()
                    fetchData(_year.value, _month.value) { onFailure("데이터를 불러오지 못했습니다.") }
                }
                is Result.Error -> {
                    onFailure("삭제 실패")
                }
            }
        }
    }
}