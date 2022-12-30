package com.example.remote_ble

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.example.remote_ble.ble.BleViewModel
import com.example.remote_ble.mqtt.MqttViewModel
import com.example.remote_ble.proto.ProtoViewModel
import com.example.remote_ble.screen.ScreenBase
import com.example.remote_ble.ui.theme.RemoteV2Theme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

class MainActivity : ComponentActivity() {
//
    val mqttViewModel by viewModels<MqttViewModel>()
    val bleViewModel by viewModels<BleViewModel>()

    private lateinit var protoViewModel: ProtoViewModel

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        protoViewModel = ViewModelProvider(this).get(ProtoViewModel::class.java)
        protoViewModel.keyUrl.observe(this, {
            ID_TV = it.idtv
            Log.i("proto", "Id TV = $ID_TV")
        })

        bleViewModel.createInstance(this@MainActivity)
        bleViewModel.checkBluetoothCompatible()

        setContent {
            RemoteV2Theme {
                mqttViewModel.connect(applicationContext = LocalContext.current)
                // A surface container using the 'background' color from the theme

                val multiplePermissionState = rememberMultiplePermissionsState(
                    permissions = listOf(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.BLUETOOTH,
                        Manifest.permission.BLUETOOTH_ADMIN,
                        Manifest.permission.BLUETOOTH_SCAN,
                        Manifest.permission.BLUETOOTH_ADVERTISE,
                        Manifest.permission.BLUETOOTH_CONNECT
                    )
                )

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
//                    Greeting("Android")
                    ScreenBase(
                        mqttViewModel = mqttViewModel,
                        protoViewModel = protoViewModel,
                        bleViewModel = bleViewModel,
                        contextActivity = this,
                        multiplePermissionsState = multiplePermissionState
                    )
//                    bleViewModel.connectToGattServer(context = this, multiplePermissionState = multiplePermissionState, macAddress = "A4:CF:12:75:9E:6A")
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