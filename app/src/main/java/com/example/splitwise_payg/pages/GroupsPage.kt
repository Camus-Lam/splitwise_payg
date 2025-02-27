import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.splitwise_payg.R

@Composable
fun GroupsPage(modifier: Modifier = Modifier) {
    BasePage(
        imageRes = R.drawable.groups,  // Update with the correct image
        messageRes = R.string.groups_guidance_message, // Update with correct string
        buttonIconRes = R.drawable.add_friend, // Update with correct icon
        buttonTextRes = R.string.start_new_group_button, // Update with correct button text
        onButtonClick = {},
        modifier = modifier
    )
}