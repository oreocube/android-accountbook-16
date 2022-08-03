package com.woowahantechcamp.account_book.ui.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.chargemap.compose.numberpicker.NumberPicker
import com.woowahantechcamp.account_book.ui.theme.White
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DatePickerDialog(
    year: Int,
    month: Int,
    onDateChanged: (Int, Int) -> Unit,
    onDismissRequest: () -> Unit
) {
    val today = LocalDate.now()
    var yearValue by remember { mutableStateOf(year) }
    var monthValue by remember { mutableStateOf(month) }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Surface(
            color = White,
            shape = RoundedCornerShape(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(width = 256.dp, height = 200.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    NumberPicker(
                        value = yearValue,
                        range = 2021..today.year,
                        onValueChange = {
                            yearValue = it
                        }
                    )
                    Text(text = "년")
                    NumberPicker(
                        value = monthValue,
                        range = if (yearValue < today.year) 1..12 else 1..today.monthValue,
                        onValueChange = { monthValue = it }
                    )
                    Text(text = "월")
                }
                TextButton(
                    onClick = {
                        onDateChanged(yearValue, monthValue)
                    },
                    modifier = Modifier.align(Alignment.BottomEnd)
                ) {
                    Text(text = "확인")
                }
            }
        }
    }
}