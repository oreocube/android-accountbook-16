package com.woowahantechcamp.account_book.ui.screen.history

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowahantechcamp.account_book.data.repository.AccountBookRepository
import com.woowahantechcamp.account_book.ui.model.CategoryModel
import com.woowahantechcamp.account_book.ui.model.HistoryModel
import com.woowahantechcamp.account_book.ui.model.PaymentModel
import com.woowahantechcamp.account_book.ui.model.Type
import com.woowahantechcamp.account_book.util.Result
import com.woowahantechcamp.account_book.util.now
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: AccountBookRepository
) : ViewModel() {

    private val _incomeCategory = MutableLiveData<List<CategoryModel>>()
    val incomeCategory: LiveData<List<CategoryModel>> = _incomeCategory

    private val _expenseCategory = MutableLiveData<List<CategoryModel>>()
    val expenseCategory: LiveData<List<CategoryModel>> = _expenseCategory

    private val _paymentMethod = MutableLiveData<List<PaymentModel>>()
    val paymentMethod: LiveData<List<PaymentModel>> = _paymentMethod

    private val _historyAll = MutableLiveData<List<HistoryModel>>()
    val historyAll: LiveData<List<HistoryModel>> = _historyAll

    private val _sumOfIncome = mutableStateOf(0)
    val sumOfIncome: MutableState<Int>
        get() = _sumOfIncome

    private val _sumOfExpense = mutableStateOf(0)
    val sumOfExpense: MutableState<Int>
        get() = _sumOfExpense

    private val _selectedItems = mutableStateListOf<Int>()
    val selectedItems: MutableList<Int>
        get() = _selectedItems

    init {
        val year = now.year
        val month = now.month.value

        fetchData(year, month)

        getAllPaymentItem()
        getAllCategoryItem()
    }


    fun addSelectedItem(id: Int) {
        _selectedItems.add(id)
    }

    fun removeSelectedItem(id: Int) {
        _selectedItems.remove(id)
    }

    fun clearSelectedItem() {
        _selectedItems.clear()
    }

    fun fetchData(year: Int, month: Int) {
        viewModelScope.launch {
            val result = repository.getAllHistories(year, month)

            if (result is Result.Success) {
                _historyAll.value = result.data
                _sumOfIncome.value =
                    result.data.filter { it.type == Type.INCOME }.sumOf { it.amount }
                _sumOfExpense.value =
                    result.data.filter { it.type == Type.EXPENSES }.sumOf { it.amount }
            }
        }
    }

    fun getHistoryItem(id: Int): HistoryModel? = _historyAll.value?.find { it.id == id }

    fun getAllCategoryItem() {
        viewModelScope.launch {
            val result = repository.getAllCategories()

            if (result is Result.Success) {
                _incomeCategory.value = result.data.filter { it.type == Type.INCOME }
                _expenseCategory.value = result.data.filter { it.type == Type.EXPENSES }
            } else {
                // TODO
            }
        }
    }

    fun saveHistoryItem(
        id: Int,
        date: String,
        type: Type,
        content: String?,
        amount: Int,
        paymentId: Int,
        categoryId: Int
    ) {
        viewModelScope.launch {
            val result = if (id > 0) {
                repository.updateHistory(
                    id = id,
                    date = date,
                    type = type.id,
                    content = content,
                    amount = amount,
                    paymentId = paymentId,
                    categoryId = categoryId
                )
            } else {
                repository.insertHistory(
                    type = type.id,
                    date = date,
                    amount = amount,
                    paymentId = paymentId,
                    categoryId = categoryId,
                    content = content
                )
            }

            if (result is Result.Success) {
                fetchData(now.year, now.monthValue)
            }
        }
    }

    fun getAllPaymentItem() {
        viewModelScope.launch {
            val result = repository.getAllPayment()

            if (result is Result.Success) {
                _paymentMethod.value = result.data
            }
        }
    }

    fun deleteAllSelectedItems() {
        viewModelScope.launch {
            val result = repository.deleteHistoryItems(_selectedItems)

            if (result is Result.Success) {
                _selectedItems.clear()
            }
        }
    }

}