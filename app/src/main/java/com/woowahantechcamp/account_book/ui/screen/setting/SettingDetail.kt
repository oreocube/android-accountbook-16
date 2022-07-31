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
import androidx.compose.ui.tooling.preview.Preview
import com.woowahantechcamp.account_book.ui.component.*
import com.woowahantechcamp.account_book.ui.model.setting.Type
import com.woowahantechcamp.account_book.ui.theme.AccountbookTheme
import com.woowahantechcamp.account_book.util.expenseColorList
import com.woowahantechcamp.account_book.util.incomeColorList

@Composable
fun SettingDetail(
    title: String,
    type: Type?
) {
    val text = rememberSaveable { mutableStateOf("") }
    val selectedColorIndex = rememberSaveable { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBarWithUpButton(title = title) {
                // TODO
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
                if (type != null) {
                    ColorPalette(
                        modifier = Modifier,
                        selectedIndex = selectedColorIndex.value,
                        colorList = if (type == Type.INCOME) incomeColorList
                        else expenseColorList
                    ) {
                        selectedColorIndex.value = it
                    }
                }
                DividerPrimary()
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

@Preview
@Composable
fun SettingDetailPreview() {
    AccountbookTheme {
        Scaffold {
            SettingDetail(title = "수입 카테고리 추가", Type.INCOME)
        }
    }
}