package com.example.splitwise_payg.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.example.splitwise_payg.R
import com.example.splitwise_payg.event.AccountEvent
import com.example.splitwise_payg.ui.theme.Dimensions.spacingLarge
import com.example.splitwise_payg.ui.theme.Dimensions.spacingMedium
import com.example.splitwise_payg.ui.theme.MAINTHEMEGREEN
import com.example.splitwise_payg.viewModel.UserViewModel

@Composable
fun LoginPage(
    modifier: Modifier = Modifier,
    viewModel: UserViewModel,
    onLoginSuccess: @Composable () -> Unit = {}
) {
    val state by viewModel.state.collectAsState()

    var emailAddress by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var showSignUpDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacingMedium),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        TextField(
            value = emailAddress,
            onValueChange = { emailAddress = it },
            placeholder = { Text(stringResource(R.string.user_login_email_placeholder)) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(spacingMedium))

        TextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text(stringResource(R.string.user_login_password_placeholder)) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(spacingLarge))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    viewModel.onAccountEvent(AccountEvent.Login(emailAddress, password))
                },
                enabled = !state.isLoading,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MAINTHEMEGREEN
                )
            ) {
                Text(stringResource(R.string.user_login_button))
            }

            Button(
                onClick = {
                    showSignUpDialog = true
                },
                enabled = !state.isLoading,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MAINTHEMEGREEN
                )
            ) {
                Text(stringResource(R.string.user_sign_up_button))
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
            Spacer(modifier = Modifier.height(spacingMedium))
            Text(
                text = stringResource(R.string.user_login_success_welcome_message, state.fullName),
                style = MaterialTheme.typography.bodyLarge
            )

            onLoginSuccess()
        }
    }

    if(showSignUpDialog) {
        SignUpDialog(viewModel = viewModel, onLoginSuccess = {onLoginSuccess}, onCancel = { showSignUpDialog = false })
    }
}