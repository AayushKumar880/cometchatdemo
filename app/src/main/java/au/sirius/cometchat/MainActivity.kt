package au.sirius.cometchat
import ConversationScreen
import MessageScreenComposable
import MessageView
import Screen
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import au.sirius.cometchat.screens.TabbedScreen
import com.cometchat.chat.core.CometChat
import com.cometchat.chat.exceptions.CometChatException
import com.cometchat.chat.models.Conversation
import com.cometchat.chat.models.Group
import com.cometchat.chat.models.User
import com.cometchat.chatuikit.shared.cometchatuikit.CometChatUIKit
import com.cometchat.chatuikit.shared.cometchatuikit.UIKitSettings


class MainActivity : ComponentActivity() {

    private val TAG = "MainActivity"

    private val appID = "2780355262a7ebfa"
    private val region = "in"
    private val authKey = "8228928bedcea7a2434060bf504f57e22727fe98"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val uiKitSettings = UIKitSettings.UIKitSettingsBuilder()
            .setRegion(region)
            .setAppId(appID)
            .setAuthKey(authKey)
            .subscribePresenceForAllUsers()
            .build()

        CometChatUIKit.init(this, uiKitSettings, object : CometChat.CallbackListener<String?>() {
            override fun onSuccess(successString: String?) {
                Log.d(TAG, "CometChat initialized successfully")
                loginUser()
            }

            override fun onError(e: CometChatException?) {
                Log.e(TAG, "Initialization error: ${e?.message}")
            }
        })
    }

    private fun loginUser() {
        CometChatUIKit.login("cometchat-uid-3", object : CometChat.CallbackListener<User>() {
            override fun onSuccess(user: User) {
                setContent {
                    val navController = rememberNavController()
                    MaterialTheme {
                        Surface {

                            //please uncomment which screen you want to see

//                          AppNavGraph(navController)
                            TabbedScreen()
//                            ConversationScreen { conversation ->
//                                val uid = (conversation.conversationWith as? User)?.uid
//                                val guid = (conversation.conversationWith as? Group)?.guid
//                                navController.navigate(Screen.MessageView.createRoute(uid, guid))
//                            }
//                            MessageView(uid = "cometchat-uid-3", guid = null, onBackPress = {}, onError = {})
//                            MessageScreenComposable (uid = "cometchat-uid-3", guid = null, onBackPress = {}, onError = {})
                        }
                    }
                }
            }

            override fun onError(e: CometChatException) {
                Log.e("Login", "Login failed: ${e.message}")
            }
        })
    }
}



//just tried to set up navigation not needed i just created the demo in jetpack compose can be ignored
// may b used in case the authentication is done
//@Composable
//fun AppNavGraph(navController: NavHostController) {
//    NavHost(navController = navController, startDestination = Screen.ConversationScreen.route) {
//
//        composable(Screen.ConversationScreen.route) {
//            ConversationScreen { conversation ->
//                val uid = (conversation.conversationWith as? User)?.uid
//                val guid = (conversation.conversationWith as? Group)?.guid
//                navController.navigate(Screen.MessageView.createRoute(uid, guid))
//            }
//        }
//
//        composable(Screen.MessageView.route) { backStackEntry ->
//            val uid = backStackEntry.arguments?.getString("uid")?.takeIf { it != "null" }
//            val guid = backStackEntry.arguments?.getString("guid")?.takeIf { it != "null" }
//
//        }
//    }
//}

