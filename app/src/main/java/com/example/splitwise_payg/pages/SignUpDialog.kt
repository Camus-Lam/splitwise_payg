package com.example.splitwise_payg.pages

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
import androidx.compose.ui.res.stringResource
import com.example.splitwise_payg.R
import com.example.splitwise_payg.event.AccountEvent
import com.example.splitwise_payg.ui.theme.Dimensions.spacingMedium
import com.example.splitwise_payg.ui.theme.MAINTHEMEGREEN
import com.example.splitwise_payg.viewModel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpDialog(
    modifier: Modifier = Modifier,
    userViewModel: UserViewModel,
    onLoginSuccess: @Composable () -> Unit = {},
    onCancel: () -> Unit = {}
) {
    val state by userViewModel.state.collectAsState()

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
                    .padding(spacingMedium),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                TextField(
                    value = fullName,
                    onValueChange = { fullName = it },
                    placeholder = { Text(stringResource(R.string.user_sign_up_full_name_placeholder)) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(spacingMedium))

                TextField(
                    value = emailAddress,
                    onValueChange = { emailAddress = it },
                    placeholder = { Text(stringResource(R.string.user_sign_up_email_placeholder)) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(spacingMedium))

                TextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = { Text(stringResource(R.string.user_sign_up_password_placeholder)) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(spacingMedium))

                TextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    placeholder = { Text(stringResource(R.string.user_sign_up_phone_number_placeholder)) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(spacingMedium))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            userViewModel.onAccountEvent(AccountEvent.SignUp(fullName, emailAddress, password, phoneNumber))
                        },
                        enabled = !state.isLoading,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MAINTHEMEGREEN
                        )
                    ) {
                        Text(stringResource(R.string.user_sign_up_button))
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
                        Text(stringResource(R.string.cancel_button))
                    }
                }

                if (state.isLoading) {
                    Spacer(modifier = Modifier.height(spacingMedium))
                    CircularProgressIndicator()
                }

                state.errorMessage?.let{ errorMessage ->
                    Spacer(modifier = Modifier.height(spacingMedium))
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                if (state.isLoggedIn) {
                    onCancel()
                    onLoginSuccess()
                }
            }
        }
    )
}