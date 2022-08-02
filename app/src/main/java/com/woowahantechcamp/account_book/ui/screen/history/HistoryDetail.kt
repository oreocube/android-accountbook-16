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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.woowahantechcamp.account_book.ui.component.*
import com.woowahantechcamp.account_book.ui.model.Type
import com.woowahantechcamp.account_book.ui.screen.setting.SettingType
import com.woowahantechcamp.account_book.util.now

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HistoryDetail(
    viewModel: HistoryViewModel,
    type: Type? = Type.INCOME,
    id: Int? = -1,
    onSettingAddClick: (SettingType) -> Unit,
    onUpPressed: () -> Unit,
    onSaved: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBarWithUpButton(
                title = if (id == -1) "내역 등록" else "내역 수정"
            ) {
                onUpPressed()
            }
        }
    ) {
        val selectedType = rememberSaveable { mutableStateOf(type) }

        val date = rememberSaveable { mutableStateOf(now) }
        val amount = rememberSaveable { mutableStateOf(0) }
        val content = rememberSaveable { mutableStateOf("") }

        val calendarVisible = rememberSaveable { mutableStateOf(false) }

        val paymentId = rememberSaveable { mutableStateOf(-1) }
        val payment = rememberSaveable { mutableStateOf("") }

        val categoryId = rememberSaveable { mutableStateOf(-1) }
        val category = rememberSaveable { mutableStateOf("") }

        val buttonEnable = if (selectedType.value == Type.INCOME) {
            (amount.value != 0) && (categoryId.value != -1)
        } else {
            (amount.value != 0) && (paymentId.value != -1) && (categoryId.value != -1)
        }

        if (calendarVisible.value) {
            CalendarDialog(
                onDateChanged = { date.value = it },
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
                    currentType = selectedType.value ?: Type.INCOME,
                    modifier = Modifier.padding(16.dp),
                    onClick = { selectedType.value = it }
                )
                InputField(title = "일자") {
                    DateInputField(date = date.value, modifier = Modifier.clickable {
                        calendarVisible.value = true
                    })
                }
                InputField(title = "금액") {
                    AmountTextInputField(amount.value) {
                        amount.value = it
                    }
                }
                if (selectedType.value == Type.EXPENSES) {
                    InputField(title = "결제수단") {
                        SelectionField(
                            displayText = payment.value,
                            list = listOf(),
                            onItemSelected = {
                                paymentId.value = it.id
                                payment.value = it.title
                            },
                            onAddSelected = {
                                onSettingAddClick(SettingType.PAYMENT)
                            }
                        )
                    }
                }
                InputField(title = "분류") {
                    SelectionField(
                        displayText = category.value,
                        list = listOf(),
                        onItemSelected = {
                            categoryId.value = it.id
                            category.value = it.title
                        },
                        onAddSelected = {
                            onSettingAddClick(
                                if (selectedType.value == Type.INCOME) SettingType.INCOME
                                else SettingType.EXPENSE
                            )
                        }
                    )
                }

                InputField(title = "내용") {
                    PlainTextInputField(content.value) {
                        content.value = it
                    }
                }
            }

            LargeButton(
                enabled = buttonEnable,
                title = if (id == -1) "등록하기" else "수정하기",
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                onSaved()
            }
        }

    }
}