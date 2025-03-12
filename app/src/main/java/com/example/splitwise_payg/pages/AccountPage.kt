package com.example.splitwise_payg.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.splitwise_payg.event.AccountEvent
import com.example.splitwise_payg.ui.components.WelcomeMessage
import com.example.splitwise_payg.ui.theme.Dimensions.spacingLarge
import com.example.splitwise_payg.ui.theme.Dimensions.spacingMedium
import com.example.splitwise_payg.ui.theme.MAINTHEMEGREEN
import com.example.splitwise_payg.viewModel.UserViewModel

@Composable
fun AccountPage(modifier: Modifier = Modifier, userViewModel: UserViewModel) {

    val state by userViewModel.state.collectAsState()

    Column (
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(spacingMedium),
        modifier = modifier.padding(spacingLarge),

        ) {

        WelcomeMessage(state.fullName)

        Text(
            text = "Full Name: ${state.fullName}",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Email Address: ${state.emailAddress}",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Phone Number: ${state.phoneNumber}",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )

        Button(
            onClick = {
                userViewModel.onAccountEvent(AccountEvent.Logout)
            },
            enabled = !state.isLoading,
            colors = ButtonDefaults.buttonColors(
                containerColor = MAINTHEMEGREEN
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Logout")
        }

    }
}