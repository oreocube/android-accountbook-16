package com.woowahantechcamp.account_book.ui.screen.setting

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.woowahantechcamp.account_book.ui.component.*
import com.woowahantechcamp.account_book.util.expenseColorList
import com.woowahantechcamp.account_book.util.incomeColorList

@Composable
fun SettingDetail(
    title: String,
    type: SettingType?,
    onUpPressed: () -> Unit
) {
    val text = rememberSaveable { mutableStateOf("") }
    val selectedColorIndex = rememberSaveable { mutableStateOf(0) }

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
                        colorList = if (type == SettingType.INCOME) incomeColorList
                        else expenseColorList
                    ) {
                        selectedColorIndex.value = it
                    }
                }
                if (type != SettingType.PAYMENT) DividerPrimary()
            }
            LargeButton(
                modifier = Modifier.align(Alignment.BottomCenter),
                enabled = text.value.isNotEmpty(),
                title = "등록하기"
            ) {
                // TODO
            }
        }
    }
}