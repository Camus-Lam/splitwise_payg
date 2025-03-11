package com.example.splitwise_payg.pages

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.splitwise_payg.db.entities.Expense
import com.example.splitwise_payg.enumClasses.OwnershipType
import com.example.splitwise_payg.enumClasses.SplitType
import com.example.splitwise_payg.event.ExpenseEvent
import com.example.splitwise_payg.viewModel.UserViewModel

@Composable
fun EditExpenseDialog(
    modifier: Modifier = Modifier,
    viewModel: UserViewModel,
    expense: Expense,
    onCancel: () -> Unit = {}
) {
    BaseExpenseDialog(
        modifier = modifier,
        viewModel = viewModel,
        amount = expense.amount.toString(),
        currency = expense.currency.currencyCode,
        targetUserId = expense.targetUserId.toString(),
        ownershipType = expense.ownershipType,
        splitType = expense.splitType,
        amountPlaceholder = { Text(expense.amount.toString()) },
        currencyPlaceholder = { Text(expense.currency.currencyCode) },
        targetUserIdPlaceholder = { Text(expense.targetUserId.toString()) },
        ownershipTypePlaceholder = { Text(stringResource(OwnershipType.getDisplayText(expense.ownershipType))) },
        splitTypePlaceholder = { Text(stringResource(SplitType.getDisplayText(expense.splitType))) },
        onConfirm = { amount, currency, targetUserId, ownershipType, splitType ->
            viewModel.onExpenseEvent(
                ExpenseEvent.editExpense(
                    amount = amount,
                    currency = currency,
                    ownershipType = ownershipType,
                    splitType = splitType,
                    targetUserId = targetUserId,
                    targetGroupId = null,
                    expense = expense
                )
            )
            onCancel()
        },
        onCancel = onCancel
    )
}