package com.woowahantechcamp.account_book.ui.screen.setting

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.woowahantechcamp.account_book.R
import com.woowahantechcamp.account_book.ui.component.CategoryTag
import com.woowahantechcamp.account_book.ui.model.setting.SettingItem
import com.woowahantechcamp.account_book.ui.model.setting.SettingItem.TextItem
import com.woowahantechcamp.account_book.ui.model.setting.SettingItem.CategoryItem
import com.woowahantechcamp.account_book.ui.theme.AccountbookTheme
import com.woowahantechcamp.account_book.ui.theme.LightPurple
import com.woowahantechcamp.account_book.ui.theme.Purple
import com.woowahantechcamp.account_book.ui.theme.Purple40

@Composable
fun SettingScreen() {
    val payments = "결제수단" to listOf(
        TextItem("현대카드"),
        TextItem("카카오뱅크 체크카드")
    )
    val expenses = "지출 카테고리" to listOf(
        CategoryItem("교통", Purple),
        CategoryItem("문화/여가", Purple),
        CategoryItem("미분류", Purple),
        CategoryItem("생활", Purple),
        CategoryItem("쇼핑/뷰티", Purple),
        CategoryItem("식비", Purple),
        CategoryItem("의료/건강", Purple)
    )
    val incomes = "수입 카테고리" to listOf(
        CategoryItem("월급", Purple),
        CategoryItem("용돈", Purple),
        CategoryItem("기타", Purple)
    )

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
        Divider(
            color = MaterialTheme.colors.primary,
            thickness = 1.dp
        )
        SettingList(
            grouped = mapOf(payments, expenses, incomes),
            onItemClick = {},
            onAddClick = {}
        )
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
    item: SettingItem,
    onItemClick: (String) -> Unit
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .clickable { onItemClick(item.title) }
        .padding(horizontal = 16.dp)
    ) {
        Divider(
            color = Purple40,
            thickness = 1.dp
        )
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
            if (item is CategoryItem) {
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
fun Footer(title: String, onAddClick: () -> Unit) {
    Column(modifier = Modifier
        .clickable { onAddClick() }
        .padding(horizontal = 16.dp)) {
        Divider(
            color = Purple40,
            thickness = 1.dp
        )
        Row(modifier = Modifier.padding(0.dp, 12.dp)) {
            Text(
                text = "$title 추가하기",
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

@Composable
fun SettingList(
    grouped: Map<String, List<SettingItem>>,
    onItemClick: (String) -> Unit,
    onAddClick: () -> Unit
) {
    LazyColumn {
        grouped.forEach { (title, list) ->
            item {
                Header(title = title)
            }
            items(list) { item ->
                Body(
                    item = item,
                    onItemClick = onItemClick
                )
            }
            item {
                Footer(
                    title = title,
                    onAddClick = onAddClick
                )
                Divider(
                    color = Purple,
                    thickness = 1.dp
                )
            }
        }
    }
}

@Preview
@Composable
fun SettingPreview() {
    AccountbookTheme {
        SettingScreen()
    }
}
