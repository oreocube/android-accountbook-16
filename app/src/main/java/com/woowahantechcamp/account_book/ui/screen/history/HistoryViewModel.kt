package com.woowahantechcamp.account_book.ui.screen.history

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowahantechcamp.account_book.data.repository.AccountBookRepository
import com.woowahantechcamp.account_book.ui.model.setting.HistoryModel
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
}