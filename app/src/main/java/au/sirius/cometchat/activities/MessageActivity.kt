package au.sirius.cometchat.activities
//import MessageScreen
//import android.os.Bundle
//import android.widget.Toast
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.activity.enableEdgeToEdge
//
//class MessageActivity : ComponentActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//
//        val uid = intent.getStringExtra("uid")
//        val guid = intent.getStringExtra("guid")
//
//        setContent {
//            MessageScreen(
//                uid = uid,
//                guid = guid,
//                onBackPress = { finish() },
//                onError = { msg -> Toast.makeText(this, msg, Toast.LENGTH_SHORT).show() }
//            )
//        }
//    }
//}
