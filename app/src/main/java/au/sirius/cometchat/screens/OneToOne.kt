import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.cometchat.chat.core.CometChat
import com.cometchat.chat.exceptions.CometChatException
import com.cometchat.chat.models.Group
import com.cometchat.chat.models.User
import com.cometchat.chatuikit.messagecomposer.CometChatMessageComposer
import com.cometchat.chatuikit.messageheader.CometChatMessageHeader
import com.cometchat.chatuikit.messagelist.CometChatMessageList

@Composable
fun MessageScreenComposable(
    uid: String?,
    guid: String?,
    onBackPress: () -> Unit,
    onError: (String) -> Unit
) {
    val context = LocalContext.current
    var loading by remember { mutableStateOf(true) }

    var headerRef by remember { mutableStateOf<CometChatMessageHeader?>(null) }
    var listRef by remember { mutableStateOf<CometChatMessageList?>(null) }
    var composerRef by remember { mutableStateOf<CometChatMessageComposer?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.systemBars.asPaddingValues())
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            AndroidView(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(bottom = 4.dp),
                factory = {
                    CometChatMessageHeader(it).apply {
                        setOnBackButtonPressed { onBackPress() }
                        headerRef = this
                    }
                }
            )

            AndroidView(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                factory = {
                    CometChatMessageList(it).also { listRef = it }
                }
            )

            AndroidView(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(top = 4.dp),
                factory = {
                    CometChatMessageComposer(it).also { composerRef = it }
                }
            )
        }

        if (loading) {
            CircularProgressIndicator(modifier = Modifier.align(androidx.compose.ui.Alignment.Center))
        }
    }

    LaunchedEffect(uid, guid) {
        loading = true
        when {
            uid != null -> {
                CometChat.getUser(uid, object : CometChat.CallbackListener<User>() {
                    override fun onSuccess(user: User) {
                        headerRef?.setUser(user)
                        listRef?.setUser(user)
                        composerRef?.setUser(user)
                        loading = false
                    }

                    override fun onError(e: CometChatException?) {
                        Log.e("MessageScreen", "User error: ${e?.message}")
                        onError("Could not find user: ${e?.message}")
                        loading = false
                    }
                })
            }

            guid != null -> {
                CometChat.getGroup(guid, object : CometChat.CallbackListener<Group>() {
                    override fun onSuccess(group: Group) {
                        headerRef?.setGroup(group)
                        listRef?.setGroup(group)
                        composerRef?.setGroup(group)
                        loading = false
                    }

                    override fun onError(e: CometChatException?) {
                        Log.e("MessageScreen", "Group error: ${e?.message}")
                        onError("Could not find group: ${e?.message}")
                        loading = false
                    }
                })
            }

            else -> {
                Log.e("MessageScreen", "No UID or GUID")
                onError("Missing user or group ID")
                loading = false
            }
        }
    }
}
