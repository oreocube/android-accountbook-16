package com.woowahantechcamp.account_book.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.woowahantechcamp.account_book.ui.theme.Purple40

@Composable
fun DividerPurple40() {
    Divider(
        thickness = 1.dp,
        color = Purple40,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}