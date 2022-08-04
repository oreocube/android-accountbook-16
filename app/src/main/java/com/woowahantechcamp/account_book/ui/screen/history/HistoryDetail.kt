package com.woowahantechcamp.account_book.ui.screen.history

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.woowahantechcamp.account_book.ui.component.*
import com.woowahantechcamp.account_book.ui.model.Type
import com.woowahantechcamp.account_book.ui.screen.setting.SettingType
import com.woowahantechcamp.account_book.util.now
import com.woowahantechcamp.account_book.util.toLocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HistoryDetail(
    viewModel: AddEditHistoryViewModel = hiltViewModel(),
    type: Type = Type.INCOME,
    id: Int = -1,
    onSettingAddClick: (SettingType) -> Unit,
    onUpPressed: () -> Unit,
    onSaved: () -> Unit
) {

    LaunchedEffect(key1 = Unit) {
        viewModel.getAllCategoryItem()
        viewModel.getAllPaymentItem()

        if (type == Type.EXPENSES) viewModel.setType(type)
        if (id > 0) viewModel.getHistoryItem(id)
    }

    val selectedType = viewModel.type.value

    val date = viewModel.date.value.toLocalDate() ?: now
    val amount = viewModel.amount.value
    val content = viewModel.content.value

    val calendarVisible = rememberSaveable { mutableStateOf(false) }

    val paymentId = viewModel.paymentId.value
    val payment = viewModel.paymentName.value
    val payments = viewModel.paymentMethod.value

    val categoryId = viewModel.categoryId.value
    val category = viewModel.categoryName.value
    val categories = when (selectedType) {
        Type.INCOME -> viewModel.incomeCategory.value
        Type.EXPENSES -> viewModel.expenseCategory.value
    }

    val buttonEnable = if (selectedType == Type.INCOME) {
        (amount != 0) && (categoryId != -1)
    } else {
        (amount != 0) && (paymentId != -1) && (categoryId != -1)
    }

    Scaffold(
        topBar = {
            TopAppBarWithUpButton(
                title = if (id == -1) "내역 등록" else "내역 수정"
            ) {
                onUpPressed()
            }
        }
    ) {

        if (calendarVisible.value) {
            CalendarDialog(
                onDateChanged = { viewModel.setDate(it.toString()) },
                onDismissRequest = { calendarVisible.value = false }
            )
        }

        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(horizontal = 16.dp)
            ) {
                SwitchButton(
                    currentType = selectedType ?: Type.INCOME,
                    modifier = Modifier.padding(16.dp),
                    onClick = { viewModel.setType(it) }
                )
                InputField(title = "일자") {
                    DateInputField(
                        date = date,
                        modifier = Modifier.clickable {
                            calendarVisible.value = true
                        })
                }
                InputField(title = "금액") {
                    AmountTextInputField(amount) {
                        viewModel.setAmount(it)
                    }
                }
                if (selectedType == Type.EXPENSES) {
                    InputField(title = "결제수단") {
                        SelectionField(
                            displayText = payment,
                            list = payments ?: listOf(),
                            onItemSelected = {
                                viewModel.setPayment(it.id, it.title)
                            },
                            onAddSelected = {
                                onSettingAddClick(SettingType.PAYMENT)
                            }
                        )
                    }
                }
                InputField(title = "분류") {
                    SelectionField(
                        displayText = category,
                        list = categories ?: listOf(),
                        onItemSelected = {
                            viewModel.setCategory(it.id, it.title)
                        },
                        onAddSelected = {
                            onSettingAddClick(
                                if (selectedType == Type.INCOME) SettingType.INCOME
                                else SettingType.EXPENSE
                            )
                        }
                    )
                }

                InputField(title = "내용") {
                    PlainTextInputField(content) {
                        viewModel.setContent(it)
                    }
                }
            }

            LargeButton(
                enabled = buttonEnable,
                title = if (id == -1) "등록하기" else "수정하기",
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                viewModel.saveHistoryItem(id)
                onSaved()
            }
        }

    }
}