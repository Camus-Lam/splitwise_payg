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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.splitwise_payg.event.ExpenseEvent
import com.example.splitwise_payg.ui.components.MAINTHEMEGREEN
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
    var currency by remember { mutableStateOf("CAD") }
    var targetUserId by remember { mutableStateOf("") }
    var ownershipType by remember {mutableStateOf("") }
    var splitType by remember {mutableStateOf("") }
    var currencyExpanded by remember { mutableStateOf(false) }
    var ownershipExpanded by remember { mutableStateOf(false) }
    var splitExpanded by remember { mutableStateOf(false) }

    BasicAlertDialog(
        onDismissRequest = { onCancel() },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
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
                    placeholder = { Text("Expense Amount (e.g., 12.34)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                ExposedDropdownMenuBox(
                    expanded = currencyExpanded,
                    onExpandedChange = { currencyExpanded = !currencyExpanded }
                ) {
                    TextField(
                        value = currency,
                        onValueChange = {},
                        readOnly = true,
                        placeholder = { Text("Expense Currency") },
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
                            text = { Text("CAD") },
                            onClick = {
                                currency = "CAD"
                                currencyExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("USD") },
                            onClick = {
                                currency = "USD"
                                currencyExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("JPY") },
                            onClick = {
                                currency = "JPY"
                                currencyExpanded = false
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = targetUserId,
                    onValueChange = { newValue ->
                        if (newValue.isEmpty() || (newValue.all { it.isDigit() } && newValue.toLong() > 0)) {
                            targetUserId = newValue
                        }
                    },
                    placeholder = { Text("User to split with (User ID)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                ExposedDropdownMenuBox(
                    expanded = ownershipExpanded,
                    onExpandedChange = { ownershipExpanded = !ownershipExpanded }
                ) {
                    TextField(
                        value = ownershipType,
                        onValueChange = {},
                        readOnly = true,
                        placeholder = { Text("Are you owing or paid?") },
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
                            text = { Text("Paid") },
                            onClick = {
                                ownershipType = "Paid"
                                ownershipExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Owing") },
                            onClick = {
                                ownershipType = "Owing"
                                ownershipExpanded = false
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                ExposedDropdownMenuBox(
                    expanded = splitExpanded,
                    onExpandedChange = { splitExpanded = !splitExpanded }
                ) {
                    TextField(
                        value = splitType,
                        onValueChange = {},
                        readOnly = true,
                        placeholder = { Text("Split type") },
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
                            text = { Text("Even") },
                            onClick = {
                                splitType = "Even"
                                splitExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Full") },
                            onClick = {
                                splitType = "Full"
                                splitExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Custom") },
                            onClick = {
                                splitType = "Custom"
                                splitExpanded = false
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

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
                            containerColor = Color(MAINTHEMEGREEN)
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
                            containerColor = Color(MAINTHEMEGREEN)
                        )
                    ) {
                        Text("Cancel")
                    }
                }

                if (state.isLoading) {
                    Spacer(modifier = Modifier.height(16.dp))
                    CircularProgressIndicator()
                }

                if (state.errorMessage != null) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = state.errorMessage!!,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    )
}