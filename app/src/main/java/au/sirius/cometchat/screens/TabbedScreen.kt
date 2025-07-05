package au.sirius.cometchat.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import com.cometchat.chatuikit.calls.calllogs.CometChatCallLogs
import com.cometchat.chatuikit.conversations.CometChatConversations
import com.cometchat.chatuikit.groups.CometChatGroups
import com.cometchat.chatuikit.users.CometChatUsers

sealed class TabItem(val title: String, val icon: ImageVector, val route: String) {
    object Chats : TabItem("Chats", Icons.Default.Face, "chats")
    object Calls : TabItem("Calls", Icons.Default.Call, "calls")
    object Users : TabItem("Users", Icons.Default.Person, "users")
    object Groups : TabItem("Groups", Icons.Default.AccountBox, "groups")
}

@Composable
fun TabbedScreen() {
    val navController = rememberNavController()
    val items = listOf(TabItem.Chats, TabItem.Calls, TabItem.Users, TabItem.Groups)

    Scaffold(
        bottomBar = {
            NavigationBar {
                val currentDestination = navController.currentBackStackEntryAsState().value?.destination
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) },
                        selected = currentDestination?.route == screen.route,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = TabItem.Chats.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(TabItem.Chats.route) {
                AndroidView(factory = { CometChatConversations(it) })
            }
            composable(TabItem.Calls.route) {
                AndroidView(factory = { CometChatCallLogs(it) })
            }
            composable(TabItem.Users.route) {
                AndroidView(factory = { CometChatUsers(it) })
            }
            composable(TabItem.Groups.route) {
                AndroidView(factory = { CometChatGroups(it) })
            }
        }
    }
}
