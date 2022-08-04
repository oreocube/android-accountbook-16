package com.woowahantechcamp.account_book.ui.component

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun DataEmptyText(modifier: Modifier = Modifier) {
    Text(
        text = "데이터가 없습니다.",
        modifier = modifier,
        color = MaterialTheme.colors.primary,
        textAlign = TextAlign.Center
    )
}