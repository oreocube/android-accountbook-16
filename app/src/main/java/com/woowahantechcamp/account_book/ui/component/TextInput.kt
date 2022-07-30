package com.woowahantechcamp.account_book.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.woowahantechcamp.account_book.ui.theme.AccountbookTheme
import com.woowahantechcamp.account_book.ui.theme.LightPurple

@Composable
fun InputField(
    title: String,
    content: @Composable () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = title,
                color = MaterialTheme.colors.primary,
                fontSize = 14.sp,
                modifier = Modifier.weight(1f)
            )

            Surface(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .weight(4f)
            ) {
                content()
            }
        }
        DividerPurple40()
    }
}

@Composable
fun PlainTextInputField(
    text: String,
    onChanged: (String) -> Unit
) {
    BasicTextField(
        value = text,
        onValueChange = { onChanged(it) },
        textStyle = TextStyle(
            color = MaterialTheme.colors.primary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    )
    if (text.isEmpty()) {
        Text(text = "입력하세요", color = LightPurple, fontSize = 14.sp)
    }
}

@Composable
fun AmountTextInputField(
    amount: Int,
    onChanged: (Int) -> Unit
) {
    BasicTextField(
        value = if (amount == 0) "" else amount.toString(),
        onValueChange = {
            onChanged(if (it.isEmpty()) 0 else it.toInt())
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        textStyle = TextStyle(
            color = MaterialTheme.colors.primary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
        // visualTransformation = VisualTransformation { }
    )
    if (amount == 0) {
        Text(text = "입력하세요", color = LightPurple, fontSize = 14.sp)
    }
}

@Preview
@Composable
fun Previewwww() {
    AccountbookTheme {
        val text = rememberSaveable { mutableStateOf("")}
        val content = rememberSaveable { mutableStateOf("")}
        val amount = rememberSaveable { mutableStateOf(0) }

        Column {
            InputField(title = "결제수단") {
                PlainTextInputField(text.value) {
                    text.value = it
                }
            }
            InputField(title = "내용") {
                PlainTextInputField(content.value) {
                    content.value = it
                }
            }
            InputField(title = "금액") {
                AmountTextInputField(amount.value) {
                    amount.value = it
                }
            }
        }

    }
}