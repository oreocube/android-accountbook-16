package com.woowahantechcamp.account_book.ui.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.woowahantechcamp.account_book.ui.theme.Yellow

@Composable
fun LargeButton(
    enabled: Boolean,
    title: String,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        onClick = { onClick() },
        shape = RoundedCornerShape(14.dp),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(backgroundColor = Yellow)
    ) {
        Text(
            text = title,
            fontSize = 14.sp,
            color = Color.White
        )
    }
}