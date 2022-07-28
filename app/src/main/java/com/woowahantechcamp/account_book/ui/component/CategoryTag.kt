package com.woowahantechcamp.account_book.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.woowahantechcamp.account_book.ui.theme.White

@Composable
fun CategoryTag(modifier: Modifier, title: String, color: Color) {
    Surface(
        modifier = Modifier
            .width(60.dp),
        color = color,
        shape = RoundedCornerShape(50.dp)
    ) {
        Text(
            text = title,
            fontSize = 10.sp,
            color = White,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = modifier.padding(8.dp, 4.dp)
        )
    }
}