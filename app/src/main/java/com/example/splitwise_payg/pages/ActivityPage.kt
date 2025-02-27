import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.splitwise_payg.R

@Composable
fun ActivityPage(modifier: Modifier = Modifier) {
    BasePage(
        imageRes = R.drawable.activities,
        messageRes = R.string.activity_guidance_message,
        buttonIconRes = R.drawable.activity,
        buttonTextRes = R.string.create_activity_button,
        onButtonClick = {},
        modifier = modifier
    )
}