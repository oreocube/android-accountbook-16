package com.woowahantechcamp.account_book.ui.screen.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowahantechcamp.account_book.data.repository.AccountBookRepository
import com.woowahantechcamp.account_book.ui.model.CategoryModel
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

    init {
        getAllCategoryItem()
    }

    fun addCategoryItem(type: Int, title: String, color: String) {
        viewModelScope.launch {
            categoryRepository.addCategory(type, title, color)
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
}