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
import androidx.compose.ui.res.stringResource
import com.example.splitwise_payg.R
import com.example.splitwise_payg.enumClasses.OwnershipType
import com.example.splitwise_payg.enumClasses.SplitType
import com.example.splitwise_payg.event.ExpenseEvent
import com.example.splitwise_payg.ui.theme.Dimensions.spacingMedium
import com.example.splitwise_payg.ui.theme.Fonts.expenseItemDetailsFontSize
import com.example.splitwise_payg.ui.theme.Fonts.expenseItemTitleFontSize
import com.example.splitwise_payg.viewModel.UserViewModel

@Composable
fun ExpenseListPanel(
    modifier: Modifier = Modifier,
    viewModel: UserViewModel
) {
    val state by viewModel.state.collectAsState()

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(spacingMedium),
        verticalArrangement = Arrangement.spacedBy(spacingMedium)
    ) {
        val expensesList = state.expenses?.allExpenses ?: emptyList()
        items(expensesList) {expense ->
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = stringResource(
                            R.string.expense_display_amount,
                            expense.currency.symbol,
                            expense.amount
                        ),
                        fontSize = expenseItemTitleFontSize
                    )
                    Text(
                        text = stringResource(
                            R.string.expense_display_ownership_type,
                            OwnershipType.getDisplayText(expense.ownershipType)
                        ),
                        fontSize = expenseItemDetailsFontSize
                    )
                    Text(
                        text = stringResource(
                            R.string.expense_display_split_type,
                            SplitType.getDescription(expense.splitType)
                        ),
                        fontSize = expenseItemDetailsFontSize
                    )
                    expense.targetUserId?.let { targetUserId ->
                        Text(
                            text =
                            stringResource(
                                R.string.expense_display_target_user_id,
                                targetUserId
                            ),
                            fontSize = expenseItemDetailsFontSize
                        )
                    }
                }
                IconButton(onClick = {
                    viewModel.onExpenseEvent(ExpenseEvent.deleteExpense(expense))
                }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(R.string.delete_expense_button)
                    )
                }
            }
        }
    }
}

