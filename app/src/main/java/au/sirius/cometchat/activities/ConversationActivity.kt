package au.sirius.cometchat.activities
//
//import CometChatConversationScreen
//import android.content.Intent
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.activity.enableEdgeToEdge
//import com.cometchat.chat.constants.CometChatConstants
//import com.cometchat.chat.models.Conversation
//import com.cometchat.chat.models.Group
//import com.cometchat.chat.models.User
//
//class ConversationActivity : ComponentActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//
//        setContent {
//            CometChatConversationScreen(
//                onConversationClick = { conversation ->
//                    startMessageActivity(conversation)
//                }
//            )
//        }
//    }
//
//    private fun startMessageActivity(conversation: Conversation) {
//        val intent = Intent(this, MessageActivity::class.java).apply {
//            when (conversation.conversationType) {
//                CometChatConstants.CONVERSATION_TYPE_GROUP -> {
//                    val group = conversation.conversationWith as Group
//                    putExtra("guid", group.guid)
//                }
//                else -> {
//                    val user = conversation.conversationWith as User
//                    putExtra("uid", user.uid)
//                }
//            }
//        }
//        startActivity(intent)
//    }
//}
