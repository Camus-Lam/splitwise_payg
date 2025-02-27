package com.example.splitwise_payg.pages

import ActivityPage
import FriendsPage
import GroupsPage
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.splitwise_payg.R
import com.example.splitwise_payg.ui.components.BottomBar
import com.example.splitwise_payg.ui.components.MAINTHEMEGREEN
import com.example.splitwise_payg.ui.components.TopBar

@Preview
@Composable
fun MainScreen(modifier: Modifier = Modifier) {

    val textStyle = MaterialTheme.typography.labelLarge
    val fontSize = textStyle.fontSize

    var selectedIndex by remember {
        mutableIntStateOf(0)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar()
        },
        bottomBar = {
            BottomBar(
                selectedIndex = selectedIndex,
                onItemSelected = { newIndex -> selectedIndex = newIndex}
            )
        },
        floatingActionButton = {
            Button(
                onClick = {},
                modifier = Modifier
                    .padding(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(MAINTHEMEGREEN)
                )
            ) {
                Icon(
                    painter = painterResource(R.drawable.calculator),
                    contentDescription = stringResource(R.string.add_expense_button),
                    modifier = Modifier
                        .size(fontSize.value.dp * 1.5f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.add_expense_button)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        ContentSection(modifier = Modifier.padding(innerPadding), selectedIndex)
    }
}

@Composable
fun ContentSection(modifier: Modifier = Modifier, selectedIndex: Int) {
    when (selectedIndex) {
        0 -> GroupsPage(modifier = modifier)
        1 -> FriendsPage(modifier = modifier)
        2 -> ActivityPage(modifier = modifier)
        3 -> AccountPage(modifier = modifier)
    }
}