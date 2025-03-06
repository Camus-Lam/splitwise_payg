package com.example.splitwise_payg

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.unit.dp
import com.example.splitwise_payg.event.AccountEvent
import com.example.splitwise_payg.ui.components.MAINTHEMEGREEN
import com.example.splitwise_payg.viewModel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpDialog(
    modifier: Modifier = Modifier,
    viewModel: UserViewModel,
    onLoginSuccess: @Composable () -> Unit = {},
    onCancel: () -> Unit = {}
) {
    val state by viewModel.state.collectAsState()

    var fullName by remember { mutableStateOf("") }
    var emailAddress by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var phoneNumber by remember {mutableStateOf("") }

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
                    value = fullName,
                    onValueChange = { fullName = it },
                    placeholder = { Text("Full Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = emailAddress,
                    onValueChange = { emailAddress = it },
                    placeholder = { Text("Email Address") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = { Text("Password") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    placeholder = { Text("Phone Number (Optional)") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Buttons Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            viewModel.onAccountEvent(AccountEvent.SignUp(fullName, emailAddress, password, phoneNumber))
                        },
                        enabled = !state.isLoading,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(MAINTHEMEGREEN)
                        )
                    ) {
                        Text("Sign Up")
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

                // Loading Indicator
                if (state.isLoading) {
                    Spacer(modifier = Modifier.height(16.dp))
                    CircularProgressIndicator()
                }

                // Error Message
                if (state.errorMessage != null) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = state.errorMessage!!,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                // Success Message (optional)
                if (state.isLoggedIn) {
                    onCancel()
                    onLoginSuccess()
                }
            }
        }
    )
}