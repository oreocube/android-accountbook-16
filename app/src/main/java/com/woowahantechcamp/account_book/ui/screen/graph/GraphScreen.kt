package com.woowahantechcamp.account_book.ui.screen.graph

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.woowahantechcamp.account_book.ui.component.*
import com.woowahantechcamp.account_book.ui.model.StatisticModel
import com.woowahantechcamp.account_book.ui.screen.main.MainViewModel
import com.woowahantechcamp.account_book.ui.theme.Red
import com.woowahantechcamp.account_book.util.expenseColorList
import com.woowahantechcamp.account_book.util.toCurrency
import kotlin.math.roundToInt

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GraphScreen(
    mainViewModel: MainViewModel,
    viewModel: GraphViewModel = hiltViewModel()
) {
    val year = mainViewModel.currentDate.value.year
    val month = mainViewModel.currentDate.value.monthValue
    val isDatePickerDialogVisible = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = month) {
        viewModel.fetchData(year, month)
    }

    val list = viewModel.statistics.value
    val sumOfExpense = list.sumOf { it.sum }

    Scaffold(
        topBar = {
            TopAppBarWithMonth(
                year = year,
                month = month,
                onDateClick = { isDatePickerDialogVisible.value = true },
                onPrevMonthClick = {
                    mainViewModel.moveToPrevMonth()
                    viewModel.fetchData(
                        year = mainViewModel.currentDate.value.year,
                        month = mainViewModel.currentDate.value.monthValue
                    )
                },
                onNextMonthClick = {
                    mainViewModel.moveToNextMonth()
                    viewModel.fetchData(
                        year = mainViewModel.currentDate.value.year,
                        month = mainViewModel.currentDate.value.monthValue
                    )
                }
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
        if (list.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize()) {
                DataEmptyText(modifier = Modifier.align(Alignment.Center))
            }
        } else {
            Column(modifier = Modifier.fillMaxSize()) {
                GraphHeader(sumOfExpense = sumOfExpense)
                DividerPurple40()
                Spacer(modifier = Modifier.height(24.dp))
                AnimatedCircle(
                    values = list,
                    total = sumOfExpense,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))
                SummaryList(list = list, total = sumOfExpense)
                DividerPrimary()
            }
        }


    }
}

@Composable
fun GraphHeader(
    sumOfExpense: Long
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = "이번 달 총 지출 금액",
            color = MaterialTheme.colors.primary,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            modifier = Modifier.align(Alignment.CenterStart)
        )
        Text(
            text = sumOfExpense.toCurrency(),
            color = Red,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            modifier = Modifier.align(Alignment.CenterEnd)
        )
    }
}

@Composable
fun SummaryList(
    list: List<StatisticModel>,
    total: Long
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        items(list) { item ->
            SummaryItem(item = item, total = total)
            DividerPurple40()
        }
        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun SummaryItem(
    item: StatisticModel,
    total: Long
) {
    val rate = ((item.sum / total.toFloat()) * 100).roundToInt()

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CategoryTag(title = item.categoryTitle, color = expenseColorList[item.color])
        Text(
            text = item.sum.toCurrency(),
            color = MaterialTheme.colors.primary,
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
        )
        Text(
            text = "$rate%",
            color = MaterialTheme.colors.primary,
            fontWeight = FontWeight.Bold
        )
    }
}