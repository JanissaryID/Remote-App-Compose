package com.example.remote.screens

import android.app.Notification
import android.content.Context
import android.os.Build
import android.util.Log
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
import com.example.remote.POWER
import com.example.remote.R
import com.example.remote.SOURCE
import com.example.remote.STAT_SCREEN
import com.example.remote.api.RemoteViewModel
import com.example.remote.component.ButtonView
import com.example.remote.mqtt.MQTTClientViewModel
import com.example.remote.mqtt.MQTTConnectionParams
import com.example.remote.ui.theme.RemoteTheme
import org.apache.http.params.HttpConnectionParams

//@RequiresApi(Build.VERSION_CODES.N)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScreenBase(
    remoteViewModel: RemoteViewModel,
//    mqttClientViewModel: MQTTClientViewModel,
//    connectionParams: MQTTConnectionParams,
//    notification: Notification
//    applicationContext: Context
) {
    val context = LocalContext.current

    var selectedScreen by remember { mutableStateOf(false) }

//    remoteViewModel.getInformation()

    ConstraintLayout(modifier = Modifier
        .padding(start = 32.dp, end = 32.dp, top = 32.dp, bottom = 32.dp)
        .fillMaxSize()
    ) {
        val (
            buttonInfo,
            buttonPower,
            buttonSource,
            informationSurface,
            buttonGetData,
            buttonConnect
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
//            remoteViewModel.insertCommand(command = SOURCE)
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
//        if(!selectedScreen){
//            ButtonView(
//                descriptionContent = "Connect",
//                buttonIcon = false,
//                title = "Connect",
//                enable = true,
//                icon = R.drawable.ic_baseline_power_settings_new_48,
//                roundedShape = 100,
//                powerButton = false,
//                width = 320,
//                modifier = modifier.constrainAs(buttonConnect){
//                    bottom.linkTo(buttonGetData.top, 16.dp)
//                    end.linkTo(parent.end)
//                    start.linkTo(parent.start)
//                }
//            ) {
//
//            }
//
//            ButtonView(
//                descriptionContent = "Get Data",
//                buttonIcon = false,
//                title = "Get Data",
//                enable = true,
//                icon = R.drawable.ic_baseline_power_settings_new_48,
//                roundedShape = 100,
//                powerButton = false,
//                width = 320,
//                modifier = modifier.constrainAs(buttonGetData){
//                    bottom.linkTo(parent.bottom)
//                    end.linkTo(parent.end)
//                    start.linkTo(parent.start)
//                }
//            ) {
//
//            }
//        }
//        else{
//            ScreenRemote(remoteViewModel = remoteViewModel)
//        }


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