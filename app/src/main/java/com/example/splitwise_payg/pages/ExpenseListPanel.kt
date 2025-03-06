package com.example.splitwise_payg.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.splitwise_payg.event.ExpenseEvent
import com.example.splitwise_payg.enumClasses.OwnershipType
import com.example.splitwise_payg.enumClasses.SplitType
import com.example.splitwise_payg.viewModel.UserViewModel

@Composable
fun ExpenseListPanel(
    modifier: Modifier = Modifier,
    viewModel: UserViewModel
) {
    val state by viewModel.state.collectAsState()

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(state.expenses!!.allExpenses) {expense ->
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "${expense.currency.symbol} ${expense.amount}",
                        fontSize = 20.sp
                    )
                    Text(
                        text = "Paid/Owing: ${OwnershipType.toString(expense.ownershipType)}",
                        fontSize = 12.sp
                    )
                    Text(
                        text = "Split: ${SplitType.toString(expense.splitType)}",
                        fontSize = 12.sp
                    )
                    Text(
                        text = "Target user Id: ${expense.targetUserId}",
                        fontSize = 12.sp
                    )
                }
                IconButton(onClick = {
                    viewModel.onExpenseEvent(ExpenseEvent.deleteExpense(expense))
                }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete expense"
                    )
                }
            }
        }
    }
}

