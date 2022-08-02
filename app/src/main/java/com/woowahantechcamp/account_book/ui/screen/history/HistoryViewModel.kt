package com.woowahantechcamp.account_book.ui.screen.history

import android.os.Build
import androidx.annotation.RequiresApi
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

    init {
        getAllPaymentItem()
        getAllCategoryItem()
    }

    init {
        val year = now.year
        val month = now.month.value

        fetchData(year, month)
    }

    private val _historyAll = MutableLiveData<List<HistoryModel>>()
    val historyAll: LiveData<List<HistoryModel>> = _historyAll

    fun fetchData(year: Int, month: Int) {
        viewModelScope.launch {
            val result = repository.getAllHistories(year, month)

            if (result is Result.Success) {
                _historyAll.value = result.data
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
                repository.updateHistory(id, date, type.id, content, amount, paymentId, categoryId)
            } else {
                repository.insertHistory(type.id, date, amount, paymentId, categoryId, content)
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

}