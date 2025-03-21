package com.example.splitwise_payg.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.splitwise_payg.R

@Composable
fun WelcomeMessage(userName: String) = Text(
    text = if (userName.isEmpty()) stringResource(R.string.welcome_message_guest) else stringResource(R.string.welcome_message, userName),
    fontWeight = FontWeight.Bold,
    modifier = Modifier.fillMaxWidth(),
    textAlign = TextAlign.Start
)

