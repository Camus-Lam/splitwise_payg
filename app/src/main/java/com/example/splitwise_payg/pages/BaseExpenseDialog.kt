package com.example.splitwise_payg.pages

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
import com.example.splitwise_payg.R
import com.example.splitwise_payg.enumClasses.CurrencyCode
import com.example.splitwise_payg.enumClasses.OwnershipType
import com.example.splitwise_payg.enumClasses.SplitType
import com.example.splitwise_payg.ui.theme.Dimensions.spacingMedium
import com.example.splitwise_payg.ui.theme.MAINTHEMEGREEN
import com.example.splitwise_payg.viewModel.ExpenseViewModel
import java.math.BigDecimal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseExpenseDialog(
    modifier: Modifier = Modifier,
    expenseViewModel: ExpenseViewModel,
    amount: String,
    currency: String,
    targetUserId: String,
    ownershipType: OwnershipType,
    splitType: SplitType,
    amountPlaceholder: @Composable () -> Unit,
    currencyPlaceholder: @Composable () -> Unit,
    targetUserIdPlaceholder: @Composable () -> Unit,
    ownershipTypePlaceholder: @Composable () -> Unit,
    splitTypePlaceholder: @Composable () -> Unit,
    onConfirm: (String, String, String, OwnershipType, SplitType) -> Unit,
    onCancel: () -> Unit = {}
) {
    val state by expenseViewModel.state.collectAsState()

    var currentAmount by remember { mutableStateOf(amount) }
    var currentCurrency by remember { mutableStateOf(currency) }
    var currentTargetUserId by remember { mutableStateOf(targetUserId) }
    var currentOwnershipType by remember { mutableStateOf(ownershipType) }
    var currentSplitType by remember { mutableStateOf(splitType) }
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
                    value = currentAmount,
                    onValueChange = { newValue ->
                        if (newValue.isEmpty()) {
                            currentAmount = ""
                        } else if (newValue.matches(Regex("^\\d*\\.?\\d*$"))) {
                            try {
                                if (BigDecimal(newValue) > BigDecimal.ZERO) {
                                    currentAmount = newValue
                                }
                            } catch (_: NumberFormatException) {
                            }
                        }
                    },
                    placeholder = amountPlaceholder,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(spacingMedium))

                ExposedDropdownMenuBox(
                    expanded = currencyExpanded,
                    onExpandedChange = { currencyExpanded = !currencyExpanded }
                ) {
                    TextField(
                        value = currentCurrency,
                        onValueChange = {},
                        readOnly = true,
                        placeholder = currencyPlaceholder,
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
                                currentCurrency = CurrencyCode.CAD.code
                                currencyExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(CurrencyCode.USD.code) },
                            onClick = {
                                currentCurrency = CurrencyCode.USD.code
                                currencyExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(CurrencyCode.JPY.code) },
                            onClick = {
                                currentCurrency = CurrencyCode.JPY.code
                                currencyExpanded = false
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(spacingMedium))

                TextField(
                    value = currentTargetUserId,
                    onValueChange = { newValue ->
                        if (newValue.isEmpty() || (newValue.all { it.isDigit() } && newValue.toLong() > 0)) {
                            currentTargetUserId = newValue
                        }
                    },
                    placeholder = targetUserIdPlaceholder,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(spacingMedium))

                ExposedDropdownMenuBox(
                    expanded = ownershipExpanded,
                    onExpandedChange = { ownershipExpanded = !ownershipExpanded }
                ) {
                    TextField(
                        value = stringResource(OwnershipType.getDisplayText(currentOwnershipType)),
                        onValueChange = {},
                        readOnly = true,
                        placeholder = ownershipTypePlaceholder,
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
                                currentOwnershipType = OwnershipType.CREATOR_PAID
                                ownershipExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.owing_ownership_display_text)) },
                            onClick = {
                                currentOwnershipType = OwnershipType.TARGET_PAID
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
                        value = stringResource(SplitType.getDisplayText(currentSplitType)),
                        onValueChange = {},
                        readOnly = true,
                        placeholder = splitTypePlaceholder,
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
                                currentSplitType = SplitType.EVEN
                                splitExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.full_split_display_text)) },
                            onClick = {
                                currentSplitType = SplitType.FULL
                                splitExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.custom_split_display_text)) },
                            onClick = {
                                currentSplitType = SplitType.CUSTOM
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
                            onConfirm(
                                currentAmount,
                                currentCurrency,
                                currentTargetUserId,
                                currentOwnershipType,
                                currentSplitType
                            )
                        },
                        enabled = !state.isLoading,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MAINTHEMEGREEN
                        )
                    ) {
                        Text(stringResource(R.string.confirm_button))
                    }

                    Button(
                        onClick = { onCancel() },
                        enabled = !state.isLoading,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MAINTHEMEGREEN
                        )
                    ) {
                        Text(stringResource(R.string.cancel_button))
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