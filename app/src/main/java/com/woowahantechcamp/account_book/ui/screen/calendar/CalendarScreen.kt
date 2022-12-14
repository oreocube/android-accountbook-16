package com.woowahantechcamp.account_book.ui.screen.calendar

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.woowahantechcamp.account_book.ui.component.DataEmptyText
import com.woowahantechcamp.account_book.ui.component.DatePickerDialog
import com.woowahantechcamp.account_book.ui.component.DividerPurple40
import com.woowahantechcamp.account_book.ui.component.TopAppBarWithMonth
import com.woowahantechcamp.account_book.ui.model.Type
import com.woowahantechcamp.account_book.ui.screen.main.MainViewModel
import com.woowahantechcamp.account_book.ui.theme.Blue4
import com.woowahantechcamp.account_book.ui.theme.Purple
import com.woowahantechcamp.account_book.ui.theme.Purple40
import com.woowahantechcamp.account_book.ui.theme.Red
import com.woowahantechcamp.account_book.util.toCurrency

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarScreen(
    mainViewModel: MainViewModel,
    viewModel: CalendarViewModel = hiltViewModel()
) {
    val year = mainViewModel.currentDate.value.year
    val month = mainViewModel.currentDate.value.monthValue
    val context = LocalContext.current

    viewModel.fetchData(year, month) {
        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
    }

    val isDatePickerDialogVisible = remember { mutableStateOf(false) }

    val calendarData = viewModel.calendarData.value
    val income = calendarData.filter { it.type == Type.INCOME.id }.sumOf { it.value }
    val expense = calendarData.filter { it.type == Type.EXPENSES.id }.sumOf { it.value }

    Scaffold(
        topBar = {
            TopAppBarWithMonth(
                year = year,
                month = month,
                onDateClick = { isDatePickerDialogVisible.value = true },
                onPrevMonthClick = { mainViewModel.moveToPrevMonth() },
                onNextMonthClick = { mainViewModel.moveToNextMonth() }
            )
        }
    ) {
        if (isDatePickerDialogVisible.value) {
            DatePickerDialog(
                year = year,
                month = month,
                onDateChanged = { newYear, newMonth ->
                    mainViewModel.setDate(newYear, newMonth)
                    isDatePickerDialogVisible.value = false
                },
                onDismissRequest = { isDatePickerDialogVisible.value = false }
            )
        }

        if (calendarData.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize()) {
                DataEmptyText(modifier = Modifier.align(Alignment.Center))
            }
        } else {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                ) {
                    Text(
                        text = "<??????>",
                        modifier = Modifier.align(Alignment.Center),
                        color = Purple40
                    )
                }
                SummaryItem(title = "??????", amount = income.toCurrency(), color = Blue4)
                SummaryItem(
                    title = "??????",
                    amount = if (expense > 0) "-${expense.toCurrency()}" else expense.toCurrency(),
                    color = Red
                )
                SummaryItem(title = "??????", amount = (income - expense).toCurrency(), color = Purple)
            }
        }
    }
}

@Composable
fun SummaryItem(
    title: String,
    amount: String,
    color: Color
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = title,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(vertical = 8.dp),
            fontSize = 14.sp,
            color = Purple
        )
        Text(
            text = amount,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(vertical = 8.dp),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = color
        )
        DividerPurple40(modifier = Modifier.align(Alignment.BottomCenter))
    }
}