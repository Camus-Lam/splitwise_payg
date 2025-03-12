package com.example.splitwise_payg.pages

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.splitwise_payg.R
import com.example.splitwise_payg.enumClasses.CurrencyCode
import com.example.splitwise_payg.enumClasses.OwnershipType
import com.example.splitwise_payg.enumClasses.SplitType
import com.example.splitwise_payg.event.ExpenseEvent
import com.example.splitwise_payg.viewModel.ExpenseViewModel

@Composable
fun AddExpenseDialog(
    modifier: Modifier = Modifier,
    expenseViewModel: ExpenseViewModel,
    onCancel: () -> Unit = {}
) {
    BaseExpenseDialog(
        modifier = modifier,
        expenseViewModel = expenseViewModel,
        amount = "",
        currency = CurrencyCode.CAD.code,
        targetUserId = "",
        ownershipType = OwnershipType.CREATOR_PAID,
        splitType = SplitType.EVEN,
        amountPlaceholder = { Text(stringResource(R.string.add_expense_amount_placeholder)) },
        currencyPlaceholder = { Text(stringResource(R.string.add_expense_currency_placeholder)) },
        targetUserIdPlaceholder = { Text(stringResource(R.string.add_expense_target_user_placeholder)) },
        ownershipTypePlaceholder = { Text(stringResource(R.string.add_expense_ownership_type_placeholder)) },
        splitTypePlaceholder = { Text(stringResource(R.string.add_expense_split_type_placeholder)) },
        onConfirm = { amount, currency, targetUserId, ownershipType, splitType ->
            expenseViewModel.onExpenseEvent(
                ExpenseEvent.addExpense(
                    amount = amount,
                    currency = currency,
                    targetUserId = targetUserId,
                    ownershipType = ownershipType,
                    splitType = splitType
                )
            )
            onCancel()
        },
        onCancel = onCancel
    )
}