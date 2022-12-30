package com.example.remote_ble.screen

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.remote_ble.*
import com.example.remote_ble.componen.ButtonView
import com.example.remote_ble.mqtt.MqttViewModel
import com.example.remote_ble.componen.DialogId
import com.example.remote_ble.R
import com.example.remote_ble.ble.BleViewModel
import com.example.remote_ble.proto.ProtoViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.example.remote_ble.STAT_COMMUNICATION

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ScreenBase(
    mqttViewModel: MqttViewModel,
    protoViewModel: ProtoViewModel,
    bleViewModel: BleViewModel,
    contextActivity: Context,
    multiplePermissionsState: MultiplePermissionsState
) {
    val context = LocalContext.current

    var selectedScreen by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(32.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ButtonView(
                descriptionContent = "Source",
                buttonIcon = false,
                title = "Source",
                enable = true,
                width = 92,
                roundedShape = 100
            ) {
                if(STAT_COMMUNICATION){
                    bleViewModel.writeRXCharacteristic(
                        multiplePermissionState = multiplePermissionsState,
                        context = context,
                        value = SOURCE.toString().toByteArray()
                    )
                }
                else{
                    mqttViewModel.publish(topic = TOPIC_PUBLISH, data = SOURCE.toString())
                }
            }

            ButtonView(
                descriptionContent = if (selectedScreen) "Info" else "Remote",
                buttonIcon = false,
                title = if (selectedScreen) "Info" else "Remote",
                enable = true,
                width = 92,
                roundedShape = 100,
                clicked = selectedScreen,
            ) {
                selectedScreen = !selectedScreen
                STAT_SCREEN = selectedScreen
                Log.i("check", "Screen $selectedScreen")
            }

            ButtonView(
                descriptionContent = "Power",
                buttonIcon = true,
                title = "Power",
                enable = true,
                icon = R.drawable.ic_baseline_power_settings_new_48,
                roundedShape = 100,
                color = MaterialTheme.colorScheme.errorContainer,
                powerButton = true
            ) {
                if(STAT_COMMUNICATION){
                    bleViewModel.writeRXCharacteristic(
                        multiplePermissionState = multiplePermissionsState,
                        context = context,
                        value = POWER.toString().toByteArray()
                    )
                }
                else{
                    mqttViewModel.publish(topic = TOPIC_PUBLISH, data = POWER.toString())
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            ButtonView(
                descriptionContent = "Connect",
                buttonIcon = false,
                title = "Connect",
                enable = if(bleViewModel.isConnect) false else true,
                width = 92,
                roundedShape = 100
            ) {
                multiplePermissionsState.launchMultiplePermissionRequest()
                bleViewModel.requestBluetoothPermission()

                bleViewModel.isConnect = true
                if(!bleViewModel.isScanning){
                    bleViewModel.connectToGattServer(context = context, multiplePermissionState = multiplePermissionsState, macAddress = "A4:CF:12:75:9E:6A")
                }
            }

            ButtonView(
                descriptionContent = "Disconnect",
                buttonIcon = false,
                title = "Disconnect",
                enable = if(bleViewModel.isConnect) true else false,
                width = 92,
                roundedShape = 100
            ) {
                bleViewModel.isConnect = false
                bleViewModel.connectToGattServer(context = context, multiplePermissionState = multiplePermissionsState, macAddress = "A4:CF:12:75:9E:6A")
            }
        }



//        Spacer(modifier = Modifier.height(32.dp))

        Box(modifier = Modifier.wrapContentHeight()){
            if (!selectedScreen)
                ScreenMonitor(mqttViewModel = mqttViewModel)
            else ScreeenRemote(
                mqttViewModel = mqttViewModel,
                bleViewModel = bleViewModel,
                context = contextActivity,
                multiplePermissionsState = multiplePermissionsState
            )
        }

        if (!selectedScreen) {
            ButtonView(
                descriptionContent = "Button ID TV",
                buttonIcon = false,
                title = "Setting ID TV",
                enable = true,
                width = 320,
                tintColor = MaterialTheme.colorScheme.onPrimary,
                roundedShape = 100,
            ) {
                IS_DIALOG_OPEN.value = true
            }
        }
    }
    if (IS_DIALOG_OPEN.value){
        DialogId(protoViewModel = protoViewModel)
    }
}