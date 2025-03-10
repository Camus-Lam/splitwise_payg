package com.example.splitwise_payg

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.example.splitwise_payg.enumClasses.CurrencyCode
import com.example.splitwise_payg.enumClasses.OwnershipType
import com.example.splitwise_payg.enumClasses.SplitType
import com.example.splitwise_payg.event.ExpenseEvent
import com.example.splitwise_payg.ui.theme.Dimensions.spacingMedium
import com.example.splitwise_payg.ui.theme.MAINTHEMEGREEN
import com.example.splitwise_payg.viewModel.UserViewModel
import java.math.BigDecimal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseDialog(
    modifier: Modifier = Modifier,
    viewModel: UserViewModel,
    onCancel: () -> Unit = {}
) {
    val state by viewModel.state.collectAsState()

    var amount by remember { mutableStateOf("") }
    var currency by remember { mutableStateOf(CurrencyCode.CAD.code) }
    var targetUserId by remember { mutableStateOf("") }
    var ownershipType by remember {mutableStateOf(OwnershipType.CREATOR_PAID) }
    var splitType by remember {mutableStateOf(SplitType.EVEN) }
    var currencyExpanded by remember { mutableStateOf(false) }
    var ownershipExpanded by remember { mutableStateOf(false) }
    var splitExpanded by remember { mutableStateOf(false) }

    BasicAlertDialog(
        onDismissRequest = { onCancel() },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(spacingMedium),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                TextField(
                    value = amount,
                    onValueChange = { newValue ->
                        if (newValue.isEmpty()) {
                            amount = ""
                        } else if (newValue.matches(Regex("^\\d*\\.?\\d*$"))) {
                            try {
                                if (BigDecimal(newValue) > BigDecimal.ZERO) {
                                    amount = newValue
                                }
                            } catch (_: NumberFormatException) {
                            }
                        }
                    },
                    placeholder = { Text(stringResource(R.string.add_expense_amount_placeholder)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(spacingMedium))

                ExposedDropdownMenuBox(
                    expanded = currencyExpanded,
                    onExpandedChange = { currencyExpanded = !currencyExpanded }
                ) {
                    TextField(
                        value = currency,
                        onValueChange = {},
                        readOnly = true,
                        placeholder = { Text(stringResource(R.string.add_expense_currency_placeholder)) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = currencyExpanded) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = currencyExpanded,
                        onDismissRequest = { currencyExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(CurrencyCode.CAD.code) },
                            onClick = {
                                currency = CurrencyCode.CAD.code
                                currencyExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(CurrencyCode.USD.code) },
                            onClick = {
                                currency = CurrencyCode.USD.code
                                currencyExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(CurrencyCode.JPY.code) },
                            onClick = {
                                currency = CurrencyCode.JPY.code
                                currencyExpanded = false
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(spacingMedium))

                TextField(
                    value = targetUserId,
                    onValueChange = { newValue ->
                        if (newValue.isEmpty() || (newValue.all { it.isDigit() } && newValue.toLong() > 0)) {
                            targetUserId = newValue
                        }
                    },
                    placeholder = { Text(stringResource(R.string.add_expense_target_user_placeholder)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(spacingMedium))

                ExposedDropdownMenuBox(
                    expanded = ownershipExpanded,
                    onExpandedChange = { ownershipExpanded = !ownershipExpanded }
                ) {
                    TextField(
                        value = stringResource(OwnershipType.getDisplayText(ownershipType)),
                        onValueChange = {},
                        readOnly = true,
                        placeholder = { Text(stringResource(R.string.add_expense_ownership_type_placeholder)) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = ownershipExpanded) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = ownershipExpanded,
                        onDismissRequest = { ownershipExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.paid_ownership_display_text)) },
                            onClick = {
                                ownershipType = OwnershipType.CREATOR_PAID
                                ownershipExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.owing_ownership_display_text)) },
                            onClick = {
                                ownershipType = OwnershipType.TARGET_PAID
                                ownershipExpanded = false
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(spacingMedium))

                ExposedDropdownMenuBox(
                    expanded = splitExpanded,
                    onExpandedChange = { splitExpanded = !splitExpanded }
                ) {
                    TextField(
                        value = stringResource(SplitType.getDisplayText(splitType)),
                        onValueChange = {},
                        readOnly = true,
                        placeholder = { Text(stringResource(R.string.add_expense_split_type_placeholder)) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = splitExpanded) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = splitExpanded,
                        onDismissRequest = { splitExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.even_split_display_text)) },
                            onClick = {
                                splitType = SplitType.EVEN
                                splitExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.full_split_display_text)) },
                            onClick = {
                                splitType = SplitType.FULL
                                splitExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.custom_split_display_text)) },
                            onClick = {
                                splitType = SplitType.CUSTOM
                                splitExpanded = false
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(spacingMedium))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            viewModel.onExpenseEvent(
                                ExpenseEvent.addExpense(
                                amount = amount,
                                currency = currency,
                                targetUserId = targetUserId,
                                ownershipType = ownershipType,
                                splitType = splitType
                            ))
                            onCancel()
                        },
                        enabled = !state.isLoading,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MAINTHEMEGREEN
                        )
                    ) {
                        Text("Add")
                    }

                    Button(
                        onClick = {
                            onCancel()
                        },
                        enabled = !state.isLoading,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MAINTHEMEGREEN
                        )
                    ) {
                        Text("Cancel")
                    }
                }

                if (state.isLoading) {
                    Spacer(modifier = Modifier.height(spacingMedium))
                    CircularProgressIndicator()
                }

                state.errorMessage?.let { errorMessage ->
                    Spacer(modifier = Modifier.height(spacingMedium))
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    )
}