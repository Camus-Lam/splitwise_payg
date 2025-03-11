package com.example.splitwise_payg.pages

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.splitwise_payg.R
import com.example.splitwise_payg.viewModel.UserViewModel

@Composable
fun ActivityPage(modifier: Modifier = Modifier, viewModel: UserViewModel) {
    BasePage(
        imageRes = R.drawable.activities,
        messageRes = R.string.activity_guidance_message,
        buttonIconRes = R.drawable.activity,
        buttonTextRes = R.string.create_activity_button,
        onButtonClick = {},
        viewModel = viewModel,
        modifier = modifier
    )
}