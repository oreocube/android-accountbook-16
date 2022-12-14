package com.woowahantechcamp.account_book.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.woowahantechcamp.account_book.R
import com.woowahantechcamp.account_book.ui.theme.Red

private val AppBarHeight = 56.dp

@Composable
fun TopAppBarWithUpButton(
    title: String,
    onUpPressed: () -> Unit
) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colors.background)
                .padding(horizontal = 16.dp)
                .height(AppBarHeight)
        ) {
            IconButton(
                modifier = Modifier.align(Alignment.CenterStart),
                onClick = { onUpPressed() }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "up",
                    tint = MaterialTheme.colors.primary
                )
            }
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = title,
                fontSize = 18.sp,
                color = MaterialTheme.colors.primary
            )
        }
        DividerPrimary()
    }
}

@Composable
fun TopAppBarWithMonth(
    year: Int,
    month: Int,
    onDateClick: () -> Unit,
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
            TextButton(
                onClick = { onDateClick() },
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "${year}??? ${month}???",
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    color = MaterialTheme.colors.primary
                )
            }
            IconButton(onClick = { onNextMonthClick() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_right),
                    contentDescription = "right",
                    tint = MaterialTheme.colors.primary
                )
            }
        }
        DividerPrimary()
    }
}

@Composable
fun TopAppBarForEditMode(
    count: Int,
    onUpPressed: () -> Unit,
    onDeleteClicked: () -> Unit
) {
    Column {
        TopAppBar(
            backgroundColor = MaterialTheme.colors.background,
            elevation = 0.dp,
            contentPadding = PaddingValues(horizontal = 16.dp),
        ) {
            IconButton(onClick = { onUpPressed() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "back",
                    tint = MaterialTheme.colors.primary
                )
            }
            Text(
                text = if (count == 0) "????????????" else "$count ??? ??????",
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                color = MaterialTheme.colors.primary
            )
            IconButton(onClick = { onDeleteClicked() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_trash),
                    contentDescription = "right",
                    tint = Red
                )
            }
        }
        DividerPrimary()
    }
}