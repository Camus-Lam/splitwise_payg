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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.splitwise_payg.event.AccountEvent
import com.example.splitwise_payg.ui.components.MAINTHEMEGREEN
import com.example.splitwise_payg.ui.components.WelcomeMessage
import com.example.splitwise_payg.viewModel.UserViewModel

@Composable
fun AccountPage(modifier: Modifier = Modifier, viewModel: UserViewModel) {

    val state by viewModel.state.collectAsState()

    Column (
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.padding(25.dp),

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
                viewModel.onAccountEvent(AccountEvent.Logout)
            },
            enabled = !state.isLoading,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(MAINTHEMEGREEN)
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Logout")
        }

    }
}