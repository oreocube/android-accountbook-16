package com.woowahantechcamp.account_book.ui.screen.history

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.woowahantechcamp.account_book.R
import com.woowahantechcamp.account_book.ui.component.*
import com.woowahantechcamp.account_book.ui.model.HistoryModel
import com.woowahantechcamp.account_book.ui.model.Type
import com.woowahantechcamp.account_book.ui.screen.main.MainViewModel
import com.woowahantechcamp.account_book.ui.theme.*
import com.woowahantechcamp.account_book.util.toCurrency
import com.woowahantechcamp.account_book.util.toDate
import com.woowahantechcamp.account_book.util.toFormattedDateString

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HistoryScreen(
    mainViewModel: MainViewModel,
    viewModel: HistoryViewModel,
    onHistoryItemClick: (Type, Int) -> Unit,
    onAddClick: (Type) -> Unit
) {
    val year = mainViewModel.currentDate.value.year
    val month = mainViewModel.currentDate.value.monthValue

    val isDatePickerDialogVisible = remember { mutableStateOf(false) }

    viewModel.fetchData(year, month)

    val isEditMode = viewModel.selectedItems.isNotEmpty()

    val selectedItems = viewModel.selectedItems

    val incomeChecked = rememberSaveable { mutableStateOf(true) }
    val expenseChecked = rememberSaveable { mutableStateOf(false) }

    val list: List<HistoryModel> by viewModel.historyAll.observeAsState(listOf())

    val filteredList = if (incomeChecked.value && expenseChecked.value) list
    else if (incomeChecked.value) list.filter { it.type == Type.INCOME }
    else if (expenseChecked.value) list.filter { it.type == Type.EXPENSES }
    else listOf()

    val grouped: Map<String, List<HistoryModel>> = filteredList.groupBy { it.date }

    Scaffold(
        topBar = {
            if (isEditMode) {
                TopAppBarForEditMode(
                    count = selectedItems.size,
                    onUpPressed = {
                        viewModel.clearSelectedItem()
                    },
                    onDeleteClicked = {
                        viewModel.deleteAllSelectedItems()
                    }
                )
            } else {
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
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onAddClick(
                        // 지출만 체크된 상태가 아닌 경우는 모두 수입 추가
                        if (!incomeChecked.value && expenseChecked.value) Type.EXPENSES
                        else Type.INCOME
                    )
                },
                backgroundColor = Yellow
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_plus),
                    contentDescription = "addIcon",
                    tint = White
                )
            }
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

        Column {
            FilterButton(
                enabled = isEditMode.not(),
                incomeChecked = incomeChecked.value,
                expenseChecked = expenseChecked.value,
                sumOfIncome = viewModel.sumOfIncome.value,
                sumOfExpense = viewModel.sumOfExpense.value,
                onChanged = {
                    if (it == Type.INCOME) incomeChecked.value = !incomeChecked.value
                    else expenseChecked.value = !expenseChecked.value
                },
                modifier = Modifier.padding(16.dp)
            )
            HistoryList(
                selectedItems = selectedItems,
                editMode = isEditMode,
                grouped = grouped,
                onHistoryItemClick = { type, id ->
                    if (isEditMode) {
                        if (selectedItems.contains(id)) {
                            viewModel.removeSelectedItem(id)
                        } else {
                            viewModel.addSelectedItem(id)
                        }
                    } else {
                        onHistoryItemClick(type, id)
                    }
                },
                onModeChanged = { id ->
                    viewModel.addSelectedItem(id)
                }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HistoryList(
    selectedItems: List<Int>,
    editMode: Boolean,
    grouped: Map<String, List<HistoryModel>>,
    onHistoryItemClick: (Type, Int) -> Unit,
    onModeChanged: (Int) -> Unit
) {
    LazyColumn {
        grouped.forEach { (date, list) ->
            val sumOfIncome = list.filter { it.type == Type.INCOME }.sumOf { it.amount }
            val sumOfExpenses = list.filter { it.type == Type.EXPENSES }.sumOf { it.amount }
            item {
                HistoryItemHeader(date, sumOfIncome, sumOfExpenses)
            }

            items(list) { item ->
                Divider(
                    color = Purple40,
                    thickness = 1.dp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                HistoryItem(
                    selected = selectedItems.contains(item.id),
                    editMode = editMode,
                    item = item,
                    onHistoryItemClick = {
                        onHistoryItemClick(it.type, it.id)
                    },
                    onModeChanged = { onModeChanged(it) }
                )
            }
            item {
                Divider(
                    color = MaterialTheme.colors.primary,
                    thickness = 1.dp
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HistoryItemHeader(date: String, sumOfIncome: Int, sumOfExpenses: Int) {
    Row(modifier = Modifier.padding(16.dp, 8.dp)) {
        Text(
            text = date.toDate().toFormattedDateString(),
            fontSize = 16.sp,
            color = LightPurple,
            modifier = Modifier
                .weight(1f)
                .align(Alignment.Bottom)
        )
        if (sumOfIncome > 0) {
            Text(
                text = "수입 ${sumOfIncome.toCurrency()}",
                fontSize = 12.sp,
                color = LightPurple,
                modifier = Modifier.align(Alignment.Bottom)
            )
        }
        if (sumOfExpenses > 0) {
            Text(
                text = " 지출 ${sumOfExpenses.toCurrency()}",
                fontSize = 12.sp,
                color = LightPurple,
                modifier = Modifier.align(Alignment.Bottom)
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HistoryItem(
    selected: Boolean,
    editMode: Boolean,
    item: HistoryModel,
    onHistoryItemClick: (HistoryModel) -> Unit,
    onModeChanged: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = if (selected) White else Color.Transparent)
            .combinedClickable(
                onClick = { onHistoryItemClick(item) },
                onLongClick = {
                    if (editMode.not()) {
                        onModeChanged(item.id)
                    }
                }
            )
            .padding(start = 16.dp, end = 16.dp, top = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (selected) {
            Image(
                painter = painterResource(id = R.drawable.ic_checkbox_checked),
                contentDescription = "checked",
                modifier = Modifier.padding(end = 16.dp)
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)

        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                CategoryTag(
                    modifier = Modifier.align(Alignment.CenterStart),
                    title = item.category,
                    color = item.color
                )
                if (item.type == Type.EXPENSES) {
                    Text(
                        text = item.payment ?: "",
                        fontSize = 10.sp,
                        color = Purple,
                        modifier = Modifier.align(Alignment.CenterEnd)
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                if (item.title.isNotEmpty()) {
                    Text(
                        text = item.title,
                        modifier = Modifier.align(Alignment.CenterStart),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Purple
                    )
                }
                Text(
                    text = if (item.type == Type.INCOME) "${item.amount.toCurrency()}원" else "-${item.amount.toCurrency()}원",
                    fontWeight = FontWeight.Bold,
                    color = if (item.type == Type.INCOME) Blue4 else Red,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }
        }
    }
}