package com.woowahantechcamp.account_book.ui.screen.main

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.woowahantechcamp.account_book.ui.theme.White50

@Composable
fun AccountBookBottomNavigation(
    modifier: Modifier,
    onTabSelected: (AccountBookScreen) -> Unit,
    currentScreen: String?
) {
    val items = listOf(
        AccountBookScreen.History,
        AccountBookScreen.Calendar,
        AccountBookScreen.Graph,
        AccountBookScreen.Setting
    )

    BottomNavigation(
        modifier = modifier
    ) {
        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(item.icon),
                        contentDescription = item.route
                    )
                },
                label = {
                    Text(text = stringResource(id = item.title))
                },
                selectedContentColor = MaterialTheme.colors.onPrimary,
                unselectedContentColor = White50,
                selected = currentScreen == item.route,
                onClick = { onTabSelected(item) }
            )
        }
    }
}