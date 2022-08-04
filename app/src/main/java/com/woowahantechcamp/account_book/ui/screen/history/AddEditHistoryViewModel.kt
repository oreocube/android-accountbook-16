package com.woowahantechcamp.account_book.ui.screen.history

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowahantechcamp.account_book.data.repository.AccountBookRepository
import com.woowahantechcamp.account_book.ui.model.CategoryModel
import com.woowahantechcamp.account_book.ui.model.PaymentModel
import com.woowahantechcamp.account_book.ui.model.Type
import com.woowahantechcamp.account_book.util.Result
import com.woowahantechcamp.account_book.util.now
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class AddEditHistoryViewModel @Inject constructor(
    private val repository: AccountBookRepository
) : ViewModel() {

    private val _incomeCategory = MutableLiveData<List<CategoryModel>>()
    val incomeCategory: LiveData<List<CategoryModel>> = _incomeCategory

    private val _expenseCategory = MutableLiveData<List<CategoryModel>>()
    val expenseCategory: LiveData<List<CategoryModel>> = _expenseCategory

    private val _paymentMethod = MutableLiveData<List<PaymentModel>>()
    val paymentMethod: LiveData<List<PaymentModel>> = _paymentMethod

    private val _type = mutableStateOf(Type.INCOME)
    val type = _type

    fun setType(type: Type) {
        _type.value = type
        _paymentId.value = -1
        paymentName.value = ""
        _categoryId.value = -1
        _categoryName.value = ""
    }

    private val _date = mutableStateOf(now.toString())
    val date = _date

    fun setDate(date: String) {
        _date.value = date
    }

    private val _amount = mutableStateOf(0)
    val amount = _amount

    fun setAmount(amount: Int) {
        _amount.value = amount
    }

    private val _content = mutableStateOf("")
    val content = _content

    fun setContent(content: String) {
        _content.value = content
    }

    private val _paymentId = mutableStateOf(-1)
    val paymentId = _paymentId

    private val _paymentName = mutableStateOf("")
    val paymentName = _paymentName

    fun setPayment(id: Int, title: String) {
        _paymentId.value = id
        _paymentName.value = title
    }

    private val _categoryId = mutableStateOf(-1)
    val categoryId = _categoryId

    private val _categoryName = mutableStateOf("")
    val categoryName = _categoryName

    fun setCategory(id: Int, title: String) {
        _categoryId.value = id
        _categoryName.value = title
    }

    fun getHistoryItem(id: Int) {
        viewModelScope.launch {
            val result = repository.getHistoryById(id)

            if (result is Result.Success) {
                val history = result.data

                _type.value = history.type
                _date.value = history.date
                _amount.value = history.amount
                _categoryId.value = history.categoryId
                _categoryName.value = history.category
                _content.value = history.title

                if (_type.value == Type.EXPENSES) {
                    _paymentId.value = history.paymentId!!
                    _paymentName.value = history.payment!!
                }
            }
        }
    }

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

    fun getAllPaymentItem() {
        viewModelScope.launch {
            val result = repository.getAllPayment()

            if (result is Result.Success) {
                _paymentMethod.value = result.data
            }
        }
    }

    fun saveHistoryItem(id: Int) {
        viewModelScope.launch {
            val result = if (id > 0) {
                repository.updateHistory(
                    id = id,
                    date = date.value,
                    type = type.value.id,
                    content = content.value,
                    amount = amount.value,
                    paymentId = paymentId.value,
                    categoryId = categoryId.value
                )
            } else {
                repository.insertHistory(
                    type = type.value.id,
                    date = date.value,
                    amount = amount.value,
                    paymentId = paymentId.value,
                    categoryId = categoryId.value,
                    content = content.value
                )
            }

            if (result is Result.Success) {

            }
        }
    }
}