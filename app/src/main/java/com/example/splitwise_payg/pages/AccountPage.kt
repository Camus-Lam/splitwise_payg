package com.example.splitwise_payg.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.splitwise_payg.R
import com.example.splitwise_payg.ui.components.WelcomeMessage

@Composable
fun AccountPage(modifier: Modifier = Modifier) {

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.padding(25.dp),

        ) {
        WelcomeMessage()
        Text(
            text = stringResource(R.string.account_placeholder),
            color = Color.Gray,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
    }
}