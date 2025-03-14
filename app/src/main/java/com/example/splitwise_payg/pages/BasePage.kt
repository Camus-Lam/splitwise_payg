package com.example.splitwise_payg.pages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.splitwise_payg.R
import com.example.splitwise_payg.ui.components.WelcomeMessage
import com.example.splitwise_payg.ui.theme.Borders
import com.example.splitwise_payg.ui.theme.Dimensions.spacingLarge
import com.example.splitwise_payg.ui.theme.Dimensions.spacingMedium
import com.example.splitwise_payg.ui.theme.Dimensions.spacingSmall
import com.example.splitwise_payg.ui.theme.MAINTHEMEGREEN
import com.example.splitwise_payg.viewModel.UserViewModel

@Composable
fun BasePage(
    imageRes: Int,
    messageRes: Int,
    buttonIconRes: Int,
    buttonTextRes: Int,
    onButtonClick: () -> Unit,
    userViewModel: UserViewModel,
    modifier: Modifier = Modifier
) {
    val textStyle = MaterialTheme.typography.labelLarge
    val fontSize = textStyle.fontSize
    val state by userViewModel.state.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(spacingMedium),
        modifier = modifier.padding(spacingLarge),
    ) {
        WelcomeMessage(state.fullName)
        Image(
            painter = painterResource(imageRes),
            contentDescription = stringResource(R.string.basic_image_description)
        )
        Text(
            text = stringResource(messageRes),
            color = Color.Gray,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
        OutlinedButton(
            onClick = onButtonClick,
            shape = RectangleShape,
            border = BorderStroke(Borders.buttonBorderThin, MAINTHEMEGREEN)
        ) {
            Icon(
                painter = painterResource(buttonIconRes),
                contentDescription = stringResource(buttonTextRes),
                modifier = Modifier.size(fontSize.value.dp * 1.5f),
                tint = MAINTHEMEGREEN
            )
            Spacer(modifier = Modifier.width(spacingSmall))
            Text(
                text = stringResource(buttonTextRes),
                color = MAINTHEMEGREEN
            )
        }
    }
}

