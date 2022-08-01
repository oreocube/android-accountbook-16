package com.woowahantechcamp.account_book.ui.component

import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.woowahantechcamp.account_book.ui.theme.Purple40

@Composable
fun DividerPurple40(modifier: Modifier = Modifier) {
    Divider(
        thickness = 1.dp,
        color = Purple40,
        modifier = modifier
    )
}

@Composable
fun DividerPrimary() {
    Divider(
        color = MaterialTheme.colors.primary,
        thickness = 1.dp
    )
}