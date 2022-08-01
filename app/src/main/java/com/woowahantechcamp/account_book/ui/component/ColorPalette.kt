package com.woowahantechcamp.account_book.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.woowahantechcamp.account_book.ui.screen.setting.Header
import com.woowahantechcamp.account_book.ui.theme.*
import com.woowahantechcamp.account_book.util.expenseColorList

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ColorPalette(
    modifier: Modifier,
    selectedIndex: Int,
    colorList: List<Color>,
    onSelectChanged: (Int) -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Header(title = "색상")
        DividerPurple40()
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            cells = GridCells.Fixed(10),
            verticalArrangement = Arrangement.Center
        ) {
            colorList.forEachIndexed { index, color ->
                item {
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .size(24.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(if (index == selectedIndex) 24.dp else 16.dp)
                                .align(Alignment.Center)
                                .background(color = color)
                                .clickable { onSelectChanged(index) }) {
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PalettePreview() {
    AccountbookTheme {

        val selectedColorIndex = rememberSaveable { mutableStateOf(0) }
        ColorPalette(
            modifier = Modifier,
            selectedIndex = selectedColorIndex.value,
            colorList = expenseColorList,
            onSelectChanged = { selectedColorIndex.value = it }
        )
    }
}