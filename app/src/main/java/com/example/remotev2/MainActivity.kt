package com.example.remotev2

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.example.remotev2.mqtt.MqttViewModel
import com.example.remotev2.proto.ProtoViewModel
import com.example.remotev2.screen.ScreenBase
import com.example.remotev2.ui.theme.RemoteV2Theme

class MainActivity : ComponentActivity() {

    val mqttViewModel by viewModels<MqttViewModel>()

    private lateinit var protoViewModel: ProtoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        protoViewModel = ViewModelProvider(this).get(ProtoViewModel::class.java)
        protoViewModel.keyUrl.observe(this, {
            ID_TV = it.idtv
            Log.i("proto", "Id TV = $ID_TV")
        })
//
        setContent {
            RemoteV2Theme {
                mqttViewModel.connect(applicationContext = LocalContext.current)
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
//                    Greeting("Android")
                    ScreenBase(mqttViewModel = mqttViewModel, protoViewModel = protoViewModel)
                }
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
    RemoteV2Theme {
        Greeting("Android")
    }
}