package com.example.splitwise_payg.pages

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.splitwise_payg.R
import com.example.splitwise_payg.viewModel.UserViewModel

@Composable
fun FriendsPage(modifier: Modifier = Modifier, viewModel: UserViewModel) {
    BasePage(
        imageRes = R.drawable.friends_image,
        messageRes = R.string.friends_guidance_message,
        buttonIconRes = R.drawable.add_friend,
        buttonTextRes = R.string.add_friend_button,
        onButtonClick = {},
        viewModel = viewModel,
        modifier = modifier
    )
}