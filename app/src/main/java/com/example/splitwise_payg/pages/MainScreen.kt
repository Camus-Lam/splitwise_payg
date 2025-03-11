package com.example.splitwise_payg.pages

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.splitwise_payg.event.ExpenseEvent
import com.example.splitwise_payg.R
import com.example.splitwise_payg.ui.components.BottomBar
import com.example.splitwise_payg.ui.components.TopBar
import com.example.splitwise_payg.ui.theme.Dimensions.spacingSmall
import com.example.splitwise_payg.ui.theme.MAINTHEMEGREEN
import com.example.splitwise_payg.viewModel.UserViewModel


@Composable
fun MainScreen(modifier: Modifier = Modifier, viewModel: UserViewModel) {

    val textStyle = MaterialTheme.typography.labelLarge
    val fontSize = textStyle.fontSize
    val state by viewModel.state.collectAsState()

    var selectedIndex by remember { mutableIntStateOf(0) }
    var showAddExpenseDialog by remember { mutableStateOf(false) }

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
                onClick = {
                    if (state.isLoggedIn) {
                        showAddExpenseDialog = true
                    }
                },
                modifier = Modifier
                    .padding(spacingSmall),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MAINTHEMEGREEN
                )
            ) {
                Icon(
                    painter = painterResource(R.drawable.calculator),
                    contentDescription = stringResource(R.string.add_expense_button),
                    modifier = Modifier
                        .size(fontSize.value.dp * 1.5f)
                )
                Spacer(modifier = Modifier.width(spacingSmall))
                Text(
                    text = stringResource(R.string.add_expense_button)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        ContentSection(modifier = Modifier.padding(innerPadding), selectedIndex, viewModel)
    }

    if(showAddExpenseDialog) {
        AddExpenseDialog(viewModel = viewModel, onCancel = { showAddExpenseDialog = false })
    }
}

@Composable
fun ContentSection(modifier: Modifier = Modifier, selectedIndex: Int, viewModel: UserViewModel) {
    val state by viewModel.state.collectAsState()

    when (selectedIndex) {
        0 -> GroupsPage(modifier = modifier, viewModel = viewModel)
        1 -> FriendsPage(modifier = modifier, viewModel = viewModel)
        2 -> {
            if (state.isLoggedIn){
                state.userId?.let { id ->
                    viewModel.onExpenseEvent(ExpenseEvent.showExpenses(id))
                }
                val expenses = state.expenses
                if (expenses?.allExpenses?.isNotEmpty() == true) {
                    ExpenseListPanel(modifier = modifier, viewModel = viewModel)
                } else {
                    ActivityPage(modifier = modifier, viewModel = viewModel)
                }
            } else {
                ActivityPage(modifier = modifier, viewModel = viewModel)
            }
        }
        3 -> {
            if (state.isLoggedIn) {
                AccountPage(modifier = modifier, viewModel = viewModel)
            } else {
                LoginPage(
                    modifier = modifier,
                    viewModel = viewModel,
                    onLoginSuccess = { }
                )
            }
        }
    }
}