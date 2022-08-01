package com.woowahantechcamp.account_book.ui.screen.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowahantechcamp.account_book.data.repository.AccountBookRepository
import com.woowahantechcamp.account_book.ui.model.CategoryModel
import com.woowahantechcamp.account_book.ui.model.PaymentModel
import com.woowahantechcamp.account_book.ui.model.Type
import com.woowahantechcamp.account_book.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val categoryRepository: AccountBookRepository
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

    fun saveCategoryItem(id: Int = -1, type: SettingType, title: String, color: Long) {
        viewModelScope.launch {
            val result = if (id == -1) categoryRepository.addCategory(type, title, color)
            else categoryRepository.updateCategory(id, title, color)

            if (result is Result.Success) {
                getAllCategoryItem()
            }
        }
    }

    fun savePaymentItem(id: Int = -1, title: String) {
        viewModelScope.launch {
            val result = if (id == -1) categoryRepository.addPayment(title)
            else categoryRepository.updatePayment(id, title)

            if (result is Result.Success) {
                getAllPaymentItem()
            }
        }
    }

    fun getAllCategoryItem() {
        viewModelScope.launch {
            val result = categoryRepository.getAllCategories()

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
            val result = categoryRepository.getAllPayment()

            if (result is Result.Success) {
                _paymentMethod.value = result.data
            }
        }
    }

    fun getCategoryItem(type: SettingType, id: Int) = when (type) {
        SettingType.INCOME -> {
            _incomeCategory.value?.find { it.id == id }
        }
        SettingType.EXPENSE -> {
            _expenseCategory.value?.find { it.id == id }
        }
        else -> null
    }

    fun getPaymentItem(id: Int) = _paymentMethod.value?.find { it.id == id }
}