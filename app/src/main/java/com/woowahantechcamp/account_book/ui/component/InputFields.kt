package com.woowahantechcamp.account_book.ui.component

import android.os.Build
import android.widget.CalendarView
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import com.woowahantechcamp.account_book.R
import com.woowahantechcamp.account_book.ui.model.Model
import com.woowahantechcamp.account_book.ui.theme.LightPurple
import com.woowahantechcamp.account_book.ui.theme.Purple
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateInputField(
    date: LocalDate,
    modifier: Modifier
) {
    val formatter = DateTimeFormatter.ofPattern("yyyy. M. d E요일", Locale.KOREA)

    Text(
        text = date.format(formatter),
        color = MaterialTheme.colors.primary,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        modifier = modifier
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarDialog(
    onDateChanged: (LocalDate) -> Unit,
    onDismissRequest: () -> Unit
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colors.background
        ) {
            AndroidView({
                CalendarView(it).apply {
                    setOnDateChangeListener { _, year, month, dayOfMonth ->
                        onDateChanged(LocalDate.of(year, month + 1, dayOfMonth))
                        onDismissRequest()
                    }
                }
            })
        }
    }
}

@Composable
fun SelectionField(
    displayText: String,
    list: List<Model>,
    onItemSelected: (Model) -> Unit,
    onAddSelected: () -> Unit
) {
    val expended = rememberSaveable { mutableStateOf(false) }

    Text(
        text = displayText.ifEmpty { "선택하세요" },
        color = if (displayText.isEmpty()) LightPurple else Purple,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.clickable { expended.value = true }
    )

    MaterialTheme(shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(16.dp))) {
        DropdownMenu(
            expanded = expended.value,
            modifier = Modifier
                .background(color = Color.Transparent)
                .width(220.dp)
                .border(
                    1.dp,
                    color = MaterialTheme.colors.primary,
                    shape = RoundedCornerShape(16.dp)
                ),
            onDismissRequest = { expended.value = false }
        ) {
            list.forEach {
                DropdownMenuItem(
                    onClick = {
                        onItemSelected(it)
                        expended.value = false
                    },
                    modifier = Modifier.background(color = Color.Transparent)
                ) {
                    Text(text = it.title, fontSize = 12.sp, color = Purple)
                }
            }
            DropdownMenuItem(
                onClick = {
                    onAddSelected()
                    expended.value = false
                },
                modifier = Modifier.background(color = Color.Transparent)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "추가하기",
                        fontSize = 12.sp,
                        color = Purple,
                        modifier = Modifier.weight(1f)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ic_plus),
                        contentDescription = "plus",
                        colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
                    )
                }
            }
        }
    }

}
