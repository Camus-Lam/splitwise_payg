import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.splitwise_payg.R
import com.example.splitwise_payg.viewModel.UserViewModel

@Composable
fun GroupsPage(modifier: Modifier = Modifier, viewModel: UserViewModel) {
    BasePage(
        imageRes = R.drawable.groups,
        messageRes = R.string.groups_guidance_message,
        buttonIconRes = R.drawable.add_friend,
        buttonTextRes = R.string.start_new_group_button,
        onButtonClick = {},
        viewModel = viewModel,
        modifier = modifier
    )
}