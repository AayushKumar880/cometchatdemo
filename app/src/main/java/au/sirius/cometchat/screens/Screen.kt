sealed class Screen(val route: String) {
    object ConversationScreen : Screen("conversationscreen")
    object MessageView : Screen("messageview/{uid}/{guid}") {
        fun createRoute(uid: String?, guid: String?): String {
            return "messageview/${uid ?: "null"}/${guid ?: "null"}"
        }
    }
}