package com.example.splitwise_payg.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.splitwise_payg.R
import com.example.splitwise_payg.dataClasses.NavItem
import com.example.splitwise_payg.ui.theme.Dimensions.spacingLarge
import com.example.splitwise_payg.ui.theme.MAINTHEMEGREEN

val BottomBarItems = listOf(
    NavItem(R.string.bottom_nav_bar_groups, R.drawable.group),
    NavItem(R.string.bottom_nav_bar_friends, R.drawable.friends),
    NavItem(R.string.bottom_nav_bar_activity, R.drawable.activity),
    NavItem(R.string.bottom_nav_bar_account, R.drawable.account)
)

@Composable
fun BottomBar(modifier: Modifier = Modifier, selectedIndex: Int, onItemSelected: (Int) -> Unit) {
    NavigationBar {
        BottomBarItems.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedIndex == index,
                onClick = {
                    onItemSelected(index)
                },
                icon = {
                    Icon(
                        painter = painterResource(item.icon),
                        contentDescription = stringResource(item.labelResId),
                        modifier = Modifier.size(spacingLarge)
                    )
                },
                label = {
                    Text(text = stringResource(item.labelResId))
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MAINTHEMEGREEN,
                    unselectedIconColor = Color.DarkGray,
                    selectedTextColor = MAINTHEMEGREEN,
                    unselectedTextColor = Color.DarkGray
                )
            )
        }
    }
}