package com.woowahantechcamp.account_book.ui.screen.history

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.woowahantechcamp.account_book.R
import com.woowahantechcamp.account_book.data.repository.HistoryRepository
import com.woowahantechcamp.account_book.ui.component.CategoryTag
import com.woowahantechcamp.account_book.ui.component.TopAppBarWithMonth
import com.woowahantechcamp.account_book.ui.model.setting.HistoryModel
import com.woowahantechcamp.account_book.ui.model.setting.Type
import com.woowahantechcamp.account_book.ui.theme.*

@Composable
fun HistoryScreen(year: Int, month: Int) {
    val repository = HistoryRepository()
    val grouped = repository.getAll()

    Scaffold(
        topBar = {
            TopAppBarWithMonth(
                year = year,
                month = month,
                onPrevMonthClick = {},
                onNextMonthClick = {}
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /*TODO*/ },
                backgroundColor = Yellow) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_plus),
                    contentDescription = "addIcon",
                    tint = White
                )
            }
        }
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
                    HistoryItem(item = item, onHistoryItemClick = {})
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
}

@Composable
fun HistoryItemHeader(date: String, sumOfIncome: Int, sumOfExpenses: Int) {
    Row(modifier = Modifier.padding(16.dp, 8.dp)) {
        Text(
            text = date,
            fontSize = 16.sp,
            color = LightPurple,
            modifier = Modifier
                .weight(1f)
                .align(Alignment.Bottom)
        )
        if (sumOfIncome > 0) {
            Text(
                text = "수입 $sumOfIncome",
                fontSize = 12.sp,
                color = LightPurple,
                modifier = Modifier.align(Alignment.Bottom)
            )
        }
        if (sumOfExpenses > 0) {
            Text(
                text = " 지출 $sumOfExpenses",
                fontSize = 12.sp,
                color = LightPurple,
                modifier = Modifier.align(Alignment.Bottom)
            )
        }
    }
}

@Composable
fun HistoryItem(item: HistoryModel, onHistoryItemClick: (HistoryModel) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onHistoryItemClick(item) }
            .padding(start = 16.dp, end = 16.dp, top = 8.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            CategoryTag(
                modifier = Modifier.align(Alignment.CenterStart),
                title = item.category,
                color = Color(item.color.toColorInt())
            )
            Text(
                text = item.payment,
                fontSize = 10.sp,
                color = Purple,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
        Box(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
            if (item.content != null) {
                Text(
                    text = item.content,
                    modifier = Modifier.align(Alignment.CenterStart),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Purple
                )
            }
            Text(
                text = if (item.type == Type.INCOME) "${item.amount}원" else "-${item.amount}원",
                fontWeight = FontWeight.Bold,
                color = if (item.type == Type.INCOME) Blue4 else Red,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }

    }
}

@Preview
@Composable
fun Preview() {
    AccountbookTheme {
        HistoryScreen(year = 2022, month = 7)
    }
}