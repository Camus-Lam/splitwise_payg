package com.example.splitwise_payg.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.splitwise_payg.R
import com.example.splitwise_payg.dataClasses.NavItem
import com.example.splitwise_payg.ui.theme.Dimensions.spacingLarge

val TopBarItems = listOf(
    NavItem(R.string.top_bar_search_button, R.drawable.search),
    NavItem(R.string.top_bar_add_friends_button, R.drawable.add_friend)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(modifier: Modifier = Modifier) {
    TopAppBar(
        title = { },
        actions = {
            TopBarItems.forEach { item ->
                IconButton(
                    onClick = {}
                ) {
                    Icon(
                        painter = painterResource(item.icon),
                        contentDescription = stringResource(item.labelResId),
                        tint = Color.DarkGray,
                        modifier = Modifier.size(spacingLarge)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.LightGray
        )
    )
}