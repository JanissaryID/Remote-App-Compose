package com.example.remote

import android.app.Notification
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.remote.api.RemoteViewModel
import com.example.remote.mqtt.MQTTClientViewModel
import com.example.remote.mqtt.MQTTConnectionParams
//import com.example.remote.mqtt.MQTTClientViewModel
import com.example.remote.screens.ScreenBase
import com.example.remote.screens.ScreenRemote
import com.example.remote.ui.theme.RemoteTheme
import com.example.remote.utils.foregroundStartService

class MainActivity : ComponentActivity() {

    val remoteViewModel by viewModels<RemoteViewModel>()
//    val mqttClientViewModel by viewModels<MQTTClientViewModel>()
//    val foregroundNotification: Notification = Notification()

//    val host = "tcp://mqtt.antares.id:1883"
//    val topic = "/oneM2M/resp/antares-cse/e09945e5329c4890:641f9aa460637c0f/json"
//    var connectionParams = MQTTConnectionParams("MQTTSample",host,topic,"","")

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RemoteTheme {
//                foregroundStartService("Start")
                // A surface container using the 'background' color from the theme
//                ScreenRemote(remoteViewModel = remoteViewModel, modifier = Modifier)
                ScreenBase(remoteViewModel = remoteViewModel)
//                mqttClientViewModel.create(context = contex)
//                mqttClientViewModel.create(context = applicationContext, notification = foregroundNotification)
//                mqttClientViewModel.createClient()
                remoteViewModel.coroutineBack()
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RemoteTheme {
        Greeting("Android")
    }
}