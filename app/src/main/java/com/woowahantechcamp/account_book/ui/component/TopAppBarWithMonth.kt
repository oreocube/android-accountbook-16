package com.woowahantechcamp.account_book.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.woowahantechcamp.account_book.R

@Composable
fun TopAppBarWithMonth(
    year: Int,
    month: Int,
    onPrevMonthClick: () -> Unit,
    onNextMonthClick: () -> Unit
) {
    Column {
        TopAppBar(
            backgroundColor = MaterialTheme.colors.background,
            elevation = 0.dp,
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            IconButton(onClick = { onPrevMonthClick() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_left),
                    contentDescription = "left",
                    tint = MaterialTheme.colors.primary
                )
            }
            Text(
                text = "${year}년 ${month}월",
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                color = MaterialTheme.colors.primary
            )
            IconButton(onClick = { onNextMonthClick() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_right),
                    contentDescription = "right",
                    tint = MaterialTheme.colors.primary
                )
            }
        }
        Divider(
            color = MaterialTheme.colors.primary,
            thickness = 1.dp
        )
    }
}