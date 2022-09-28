package com.example.remotev2.screen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.remotev2.*
import com.example.remotev2.componen.ButtonView
import com.example.remotev2.mqtt.MqttViewModel
import com.example.remotev2.R.drawable
import com.example.remotev2.componen.DialogId
import com.example.remotev2.proto.ProtoViewModel

@Composable
fun ScreenBase(
    mqttViewModel: MqttViewModel,
    protoViewModel: ProtoViewModel
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
                width = 108,
                roundedShape = 100
            ) {
                mqttViewModel.publish(topic = TOPIC_PUBLISH, data = SOURCE.toString())
            }

            ButtonView(
                descriptionContent = if (selectedScreen) "Info" else "Remote",
                buttonIcon = false,
                title = if (selectedScreen) "Info" else "Remote",
                enable = true,
                width = 108,
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
                icon = drawable.ic_baseline_power_settings_new_48,
                roundedShape = 100,
                color = MaterialTheme.colorScheme.errorContainer,
                powerButton = true
            ) {
                mqttViewModel.publish(topic = TOPIC_PUBLISH, data = POWER.toString())
            }
        }

//        Spacer(modifier = Modifier.height(32.dp))

        Box(modifier = Modifier.wrapContentHeight()){
            if (!selectedScreen) ScreenMonitor(mqttViewModel = mqttViewModel) else ScreeenRemote(mqttViewModel = mqttViewModel)
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