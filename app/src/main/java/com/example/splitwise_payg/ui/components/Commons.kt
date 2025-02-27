package com.example.splitwise_payg.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.splitwise_payg.R

const val MAINTHEMEGREEN = 0xFF57d455

@Composable
fun WelcomeMessage() = Text(
    text = stringResource(R.string.welcome_message),
    fontWeight = FontWeight.Bold,
    modifier = Modifier
        .fillMaxWidth(),
    textAlign = TextAlign.Start
)

