package com.woowahantechcamp.account_book.ui.screen.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.woowahantechcamp.account_book.R
import com.woowahantechcamp.account_book.ui.component.CategoryTag
import com.woowahantechcamp.account_book.ui.component.DividerPrimary
import com.woowahantechcamp.account_book.ui.component.DividerPurple40
import com.woowahantechcamp.account_book.ui.model.CategoryModel
import com.woowahantechcamp.account_book.ui.model.Model
import com.woowahantechcamp.account_book.ui.model.PaymentModel
import com.woowahantechcamp.account_book.ui.model.Type
import com.woowahantechcamp.account_book.ui.theme.LightPurple

@Composable
fun SettingScreen(
    viewModel: SettingViewModel,
    onItemClicked: (SettingType, Int) -> Unit,
    onAddClick: (SettingType) -> Unit
) {
    val payments = SettingType.PAYMENT to listOf(
        PaymentModel(1, "현대카드"),
        PaymentModel(2, "카카오뱅크 체크카드")
    )
    val incomeCategory: List<CategoryModel> by viewModel.incomeCategory.observeAsState(listOf())
    val expensesCategory: List<CategoryModel> by viewModel.expenseCategory.observeAsState(listOf())

    val incomes = SettingType.INCOME to incomeCategory
    val expenses = SettingType.EXPENSE to expensesCategory

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                Text(
                    text = "설정",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    color = MaterialTheme.colors.primary
                )
            }
        }
    ) {
        DividerPrimary()
        SettingList(
            grouped = mapOf(payments, expenses, incomes),
            onItemClick = {
                when (it) {
                    is PaymentModel -> {
                        onItemClicked(SettingType.PAYMENT, it.id)
                    }
                    is CategoryModel -> {
                        if (it.type == Type.INCOME) {
                            onItemClicked(SettingType.INCOME, it.id)
                        } else {
                            onItemClicked(SettingType.EXPENSE, it.id)
                        }
                    }
                }
            },
            onAddClick = {
                onAddClick(it)
            }
        )
    }
}

@Composable
fun SettingList(
    grouped: Map<SettingType, List<Model>>,
    onItemClick: (Model) -> Unit,
    onAddClick: (SettingType) -> Unit
) {
    LazyColumn {
        grouped.forEach { (type, list) ->
            item {
                Header(title = type.plainTitle)
            }
            items(list) { item ->
                Body(
                    item = item,
                    onItemClick = onItemClick
                )
            }
            item {
                Footer(
                    type = type,
                    onAddClick = onAddClick
                )
                DividerPrimary()
            }
        }
    }
}

@Composable
fun Header(title: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.padding(top = 16.dp))
        Text(
            text = title,
            color = LightPurple,
            fontSize = 18.sp,
            modifier = Modifier.padding(16.dp, 8.dp)
        )
    }
}

@Composable
fun Body(
    item: Model,
    onItemClick: (Model) -> Unit
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .clickable { onItemClick(item) }
        .padding(horizontal = 16.dp)
    ) {
        DividerPurple40()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
        ) {
            Text(
                text = item.title,
                color = MaterialTheme.colors.primary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .weight(1f)

            )
            if (item is CategoryModel) {
                CategoryTag(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    title = item.title,
                    color = item.color
                )
            }
        }
    }

}

@Composable
fun Footer(type: SettingType, onAddClick: (SettingType) -> Unit) {
    Column(modifier = Modifier
        .clickable { onAddClick(type) }
        .padding(horizontal = 16.dp)) {
        DividerPurple40()
        Row(modifier = Modifier.padding(0.dp, 12.dp)) {
            Text(
                text = type.addTitle,
                color = MaterialTheme.colors.primary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_plus),
                contentDescription = "plus",
                tint = MaterialTheme.colors.primary,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}

