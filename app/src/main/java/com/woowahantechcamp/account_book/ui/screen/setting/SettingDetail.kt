package com.woowahantechcamp.account_book.ui.screen.setting

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.woowahantechcamp.account_book.ui.component.*
import com.woowahantechcamp.account_book.ui.model.CategoryModel
import com.woowahantechcamp.account_book.util.expenseColorList
import com.woowahantechcamp.account_book.util.incomeColorList

@Composable
fun SettingDetail(
    viewModel: SettingViewModel,
    type: SettingType,
    id: Int = -1,
    onUpPressed: () -> Unit,
    onSaved: () -> Unit
) {
    val passedData = if (id > 0) {
        if (type == SettingType.PAYMENT) viewModel.getPaymentItem(id)
        else viewModel.getCategoryItem(type, id)
    } else null

    val title = if (id < 0) type.addTitle else type.editTitle

    val text = rememberSaveable { mutableStateOf(passedData?.title ?: "") }
    val colorList = if (type == SettingType.INCOME) incomeColorList
    else expenseColorList

    val passedDataColorIndex = passedData?.let {
        when (type) {
            SettingType.PAYMENT -> 0
            SettingType.INCOME -> {
                (it as CategoryModel).color
            }
            SettingType.EXPENSE -> {
                (it as CategoryModel).color
            }
        }
    } ?: 0

    val selectedColorIndex = rememberSaveable { mutableStateOf(passedDataColorIndex) }

    Scaffold(
        topBar = {
            TopAppBarWithUpButton(title = title) {
                onUpPressed()
            }
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
            ) {
                InputField(title = "이름") {
                    PlainTextInputField(text.value) { text.value = it }
                }
                if (type != SettingType.PAYMENT) {
                    ColorPalette(
                        modifier = Modifier,
                        selectedIndex = selectedColorIndex.value,
                        colorList = colorList
                    ) {
                        selectedColorIndex.value = it
                    }
                }
                if (type != SettingType.PAYMENT) DividerPrimary()
            }
            LargeButton(
                modifier = Modifier.align(Alignment.BottomCenter),
                enabled = text.value.isNotEmpty(),
                title = if (id < 0) "등록하기" else "수정하기"
            ) {
                if (type == SettingType.PAYMENT) {
                    viewModel.savePaymentItem(id, text.value)
                    onSaved()
                } else {
                    viewModel.saveCategoryItem(
                        id,
                        type,
                        text.value,
                        selectedColorIndex.value
                    )
                    onSaved()
                }
            }
        }
    }
}
