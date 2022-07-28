package com.woowahantechcamp.account_book.data.repository.category

import com.woowahantechcamp.account_book.data.entity.CategoryEntity
import com.woowahantechcamp.account_book.util.Result
import com.woowahantechcamp.account_book.util.UiState
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val categoryDataSource: CategoryDataSource
) {
    suspend fun getAllCategory(): UiState<List<CategoryEntity>> {
        return when (val result = categoryDataSource.getAllCategory()) {
            is Result.Success -> {
                UiState.Success(data = result.data)
            }
            is Result.Error -> {
                UiState.Error(result.exception)
            }
        }
    }

    suspend fun addCategory(type: Int, title: String, color: String) {
        categoryDataSource.insertCategory(CategoryEntity(type, title, color))
    }
}