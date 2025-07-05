import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import com.cometchat.chat.models.Conversation
import com.cometchat.chatuikit.conversations.CometChatConversations

@Composable
fun ConversationScreen(
    onConversationClick: (Conversation) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()),
            factory = { context ->
                CometChatConversations(context).apply {
                    setOnItemClick { _, _, conversation ->
                        onConversationClick(conversation)
                    }
                }
            }
        )
    }
}

