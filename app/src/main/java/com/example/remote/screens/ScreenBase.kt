package com.example.remote.screens

import android.app.Notification
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.remote.*
import com.example.remote.R
import com.example.remote.api.RemoteViewModel
import com.example.remote.component.ButtonView
import com.example.remote.component.ViewDialogWifi
import com.example.remote.proto.ProtoViewModel
import com.example.remote.tcp.TcpViewModel
import com.example.remote.ui.theme.RemoteTheme
import kotlinx.coroutines.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScreenBase(
    remoteViewModel: RemoteViewModel,
    protoViewModel: ProtoViewModel
) {
    val context = LocalContext.current

    var selectedScreen by remember { mutableStateOf(false) }

    ConstraintLayout(modifier = Modifier
        .padding(start = 32.dp, end = 32.dp, top = 32.dp, bottom = 32.dp)
        .fillMaxSize()
    ) {
        val (
            buttonInfo,
            buttonPower,
            buttonSource,
            informationSurface,
            ButtonWIfi
        ) = createRefs()
        val modifier = Modifier

        ButtonView(
            descriptionContent = "Source",
            buttonIcon = false,
            title = "Source",
            enable = true,
            width = 92,
            roundedShape = 100,
            modifier = modifier.constrainAs(buttonSource){
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            }
        ) {
            remoteViewModel.insertCommand(command = SOURCE)

        }

        ButtonView(
            descriptionContent = if (selectedScreen) "Info" else "Remote",
            buttonIcon = false,
            title = if (selectedScreen) "Info" else "Remote",
            enable = true,
            width = 92,
            roundedShape = 100,
            clicked = selectedScreen,
            modifier = modifier.constrainAs(buttonInfo){
                top.linkTo(parent.top)
                start.linkTo(buttonSource.end)
                end.linkTo(buttonPower.start)
            }
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
            powerButton = true,
            modifier = modifier.constrainAs(buttonPower){
                top.linkTo(parent.top)
                end.linkTo(parent.end)
            }
        ) {
            remoteViewModel.insertCommand(command = POWER)
        }

        Box(modifier = modifier.constrainAs(informationSurface){
            top.linkTo(buttonPower.bottom, 216.dp)
            end.linkTo(parent.end)
            start.linkTo(parent.start)
            bottom.linkTo(parent.bottom)
        }){
            if (!selectedScreen) ScreenInformation() else ScreenRemote(remoteViewModel = remoteViewModel)
        }

        if (!selectedScreen) {
            ButtonView(
                descriptionContent = "Button Wifi",
                buttonIcon = false,
                title = "Setting Wifi TV",
                enable = true,
                width = 320,
                tintColor = MaterialTheme.colorScheme.onPrimary,
                roundedShape = 100,
                modifier = modifier.constrainAs(ButtonWIfi){
                    bottom.linkTo(parent.bottom, 16.dp)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                }
            ) {
//            if (!typeButton) remoteViewModel.insertCommand(command = VOL_MINUS) else remoteViewModel.insertCommand(command = CH_MINUS)
//                Toast.makeText(context, "Setting Wifi", Toast.LENGTH_SHORT).show()
                IS_DIALOG_OPEN.value = true
//                tcpViewModel.uiScope.cancel()


//                CoroutineScope(Dispatchers.Default).launch { tcpViewModel.Connect() }
//                runBlocking { tcpViewModel.Connect() }
            }
        }
    }
    if (IS_DIALOG_OPEN.value){
        ViewDialogWifi(protoViewModel = protoViewModel)
    }
}

@RequiresApi(Build.VERSION_CODES.N)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RemoteTheme {
//        ScreenBase(remoteViewModel = RemoteViewModel(), mqttClientViewModel = MQTTClientViewModel(()))
    }
}