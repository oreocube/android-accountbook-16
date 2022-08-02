package com.woowahantechcamp.account_book.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.woowahantechcamp.account_book.R
import com.woowahantechcamp.account_book.ui.model.Type
import com.woowahantechcamp.account_book.ui.theme.LightPurple
import com.woowahantechcamp.account_book.ui.theme.White
import com.woowahantechcamp.account_book.util.toCurrency

@Composable
fun FilterButton(
    enabled: Boolean,
    incomeChecked: Boolean,
    expenseChecked: Boolean,
    sumOfIncome: Int,
    sumOfExpense: Int,
    onChanged: (Type) -> Unit,
    modifier: Modifier
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
            Type.values().forEach {
                FilterItem(
                    enabled = enabled,
                    type = it,
                    amount = if (it == Type.INCOME) sumOfIncome else sumOfExpense,
                    selected = if (it == Type.INCOME) incomeChecked else expenseChecked,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { if (enabled) onChanged(it) }
                )
            }
        }
    }
}

@Composable
fun FilterItem(
    enabled: Boolean,
    type: Type,
    amount: Int,
    selected: Boolean,
    modifier: Modifier
) {
    val backgroundColor = if (enabled) {
        if (selected) MaterialTheme.colors.primary else LightPurple
    } else {
        Color.LightGray
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = backgroundColor)
            .padding(8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier
                .size(12.dp)
                .clip(RoundedCornerShape(2.dp)),
            border = BorderStroke(1.dp, White),
            color = if (selected) White else Color.Transparent
        ) {
            if (selected) {
                Image(
                    painter = painterResource(R.drawable.ic_check),
                    contentDescription = type.title,
                )
            }
        }
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = "${type.title} ${amount.toCurrency()}",
            fontSize = 12.sp,
            color = White
        )
    }
}