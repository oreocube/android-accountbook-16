package com.woowahantechcamp.account_book.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.woowahantechcamp.account_book.ui.model.Type
import com.woowahantechcamp.account_book.ui.theme.AccountbookTheme
import com.woowahantechcamp.account_book.ui.theme.LightPurple
import com.woowahantechcamp.account_book.ui.theme.White

@Composable
fun SwitchButton(
    currentType: Type,
    modifier: Modifier = Modifier,
    onClick: (Type) -> Unit
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
        ) {
            Type.values().forEach { type ->
                SwitchButtonItem(
                    type = type,
                    selected = type == currentType,
                    onClick = { onClick(type) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun SwitchButtonItem(
    type: Type,
    selected: Boolean,
    onClick: (Type) -> Unit,
    modifier: Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(type) }
            .background(color = if (selected) MaterialTheme.colors.primary else LightPurple)
            .padding(8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = type.title,
            fontSize = 12.sp,
            color = White
        )
    }
}

@Preview
@Composable
fun PreviewSwitchButton() {
    AccountbookTheme {
        val currentType = remember { mutableStateOf(Type.INCOME) }
        SwitchButton(
            currentType = currentType.value,
            modifier = Modifier,
            onClick = {
                currentType.value = it
            })
    }
}
