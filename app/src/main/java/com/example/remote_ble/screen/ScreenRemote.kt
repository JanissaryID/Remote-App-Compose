package com.example.remote_ble.screen

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.remote_ble.*
import com.example.remote_ble.componen.ButtonGroup
import com.example.remote_ble.componen.ButtonView
import com.example.remote_ble.mqtt.MqttViewModel
import com.example.remote_ble.R.drawable
import com.example.remote_ble.ble.BleViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.example.remote_ble.STAT_COMMUNICATION

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ScreeenRemote(
    mqttViewModel: MqttViewModel,
    bleViewModel: BleViewModel,
    statCommunication: Boolean = true,
    context: Context,
    multiplePermissionsState: MultiplePermissionsState
) {

    val modifier = Modifier

    ConstraintLayout(modifier = modifier
        .wrapContentHeight()
        .fillMaxWidth()) {
        val (
            channel,
            volume,
            mute,
            zoom,
            info,
            enter,
            up,
            right,
            down,
            left,
            menu,
            exit
        ) = createRefs()

        ButtonGroup(modifier = modifier
            .width(72.dp)
            .height(216.dp)
            .constrainAs(channel) {
                top.linkTo(parent.top)
                end.linkTo(parent.end, 16.dp)
            }, title = "Channel",
            iconTop = drawable.ic_baseline_arrow_drop_up_48,
            iconBottom = drawable.ic_baseline_arrow_drop_down_48,
            typeButton = true,
            mqttViewModel = mqttViewModel,
            multiplePermissionsState = multiplePermissionsState,
            context = context,
            bleViewModel = bleViewModel
        )

        ButtonGroup(modifier = modifier
            .width(72.dp)
            .height(216.dp)
            .constrainAs(volume) {
                top.linkTo(parent.top)
                start.linkTo(parent.start, 16.dp)
            }, title = "Volume",
            iconTop = drawable.ic_baseline_add_48,
            iconBottom = drawable.ic_baseline_remove_48,
            typeButton = false,
            mqttViewModel = mqttViewModel,
            multiplePermissionsState = multiplePermissionsState,
            context = context,
            bleViewModel = bleViewModel
        )

        ButtonView(
            descriptionContent = "Mute",
            buttonIcon = false,
            title = "Mute",
            enable = true,
            width = 92,
            roundedShape = 100,
            modifier = modifier.constrainAs(mute){
                top.linkTo(volume.top, 18.dp)
                start.linkTo(volume.end)
                end.linkTo(channel.start)
            }
        ) {
            if(STAT_COMMUNICATION){
                bleViewModel.writeRXCharacteristic(
                    multiplePermissionState = multiplePermissionsState,
                    context = context,
                    value = MUTE.toString().toByteArray()
                )
            }
            else{
                mqttViewModel.publish(topic = TOPIC_PUBLISH, data = MUTE.toString())
            }

        }

        ButtonView(
            descriptionContent = "Zoom",
            buttonIcon = false,
            title = "Zoom",
            enable = true,
            width = 92,
            roundedShape = 100,
            modifier = modifier.constrainAs(zoom){
                start.linkTo(volume.end)
                bottom.linkTo(channel.bottom, 18.dp)
                end.linkTo(channel.start)
            }
        ) {
            if(STAT_COMMUNICATION){
                bleViewModel.writeRXCharacteristic(
                    multiplePermissionState = multiplePermissionsState,
                    context = context,
                    value = ZOOM.toString().toByteArray()
                )
            }
            else{
                mqttViewModel.publish(topic = TOPIC_PUBLISH, data = ZOOM.toString())
            }
//            Toast.makeText(context, "Source", Toast.LENGTH_SHORT).show()
        }

        ButtonView(
            descriptionContent = "Fav",
            buttonIcon = false,
            title = "Fav",
            enable = true,
            width = 92,
            roundedShape = 100,
            modifier = modifier.constrainAs(info){
                start.linkTo(parent.start)
                top.linkTo(volume.bottom, 18.dp)
            }
        ) {
            if(STAT_COMMUNICATION){
                bleViewModel.writeRXCharacteristic(
                    multiplePermissionState = multiplePermissionsState,
                    context = context,
                    value = FAV.toString().toByteArray()
                )
            }
            else{
                mqttViewModel.publish(topic = TOPIC_PUBLISH, data = FAV.toString())
            }
//            Toast.makeText(context, "Source", Toast.LENGTH_SHORT).show()
        }

        ButtonView(
            descriptionContent = "Up",
            buttonIcon = true,
            title = "Up",
            enable = true,
            icon = drawable.ic_baseline_arrow_drop_up_48,
            roundedShape = 100,
            tintColor = MaterialTheme.colorScheme.onPrimary,
            modifier = modifier.constrainAs(up){
                top.linkTo(volume.bottom, 18.dp)
                start.linkTo(volume.end)
                end.linkTo(channel.start)
            }
        ) {
            if(STAT_COMMUNICATION){
                bleViewModel.writeRXCharacteristic(
                    multiplePermissionState = multiplePermissionsState,
                    context = context,
                    value = UP.toString().toByteArray()
                )
            }
            else{
                mqttViewModel.publish(topic = TOPIC_PUBLISH, data = UP.toString())
            }
//            Toast.makeText(context, "Power", Toast.LENGTH_SHORT).show()
        }

        ButtonView(
            descriptionContent = "Enter",
            buttonIcon = false,
            title = "Enter",
            enable = true,
            icon = drawable.ic_baseline_arrow_drop_up_48,
            roundedShape = 100,
            heigh = 144,
            width = 144,
            modifier = modifier.constrainAs(enter){
                top.linkTo(up.bottom, 18.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        ) {
            if(STAT_COMMUNICATION){
                bleViewModel.writeRXCharacteristic(
                    multiplePermissionState = multiplePermissionsState,
                    context = context,
                    value = SELECT.toString().toByteArray()
                )
            }
            else{
                mqttViewModel.publish(topic = TOPIC_PUBLISH, data = SELECT.toString())
            }
//            Toast.makeText(context, "Power", Toast.LENGTH_SHORT).show()
        }

        ButtonView(
            descriptionContent = "Down",
            buttonIcon = true,
            title = "Down",
            enable = true,
            icon = drawable.ic_baseline_arrow_drop_down_48,
            roundedShape = 100,
            tintColor = MaterialTheme.colorScheme.onPrimary,
            modifier = modifier.constrainAs(down){
                top.linkTo(enter.bottom, 18.dp)
                start.linkTo(volume.end)
                end.linkTo(channel.start)
            }
        ) {
            if(STAT_COMMUNICATION){
                bleViewModel.writeRXCharacteristic(
                    multiplePermissionState = multiplePermissionsState,
                    context = context,
                    value = DOWN.toString().toByteArray()
                )
            }
            else{
                mqttViewModel.publish(topic = TOPIC_PUBLISH, data = DOWN.toString())
            }
//            Toast.makeText(context, "Power", Toast.LENGTH_SHORT).show()
        }

        ButtonView(
            descriptionContent = "Left",
            buttonIcon = true,
            title = "Left",
            enable = true,
            icon = drawable.ic_baseline_arrow_left_48,
            roundedShape = 100,
            tintColor = MaterialTheme.colorScheme.onPrimary,
            modifier = modifier.constrainAs(left){
                top.linkTo(enter.top)
                end.linkTo(enter.start, 18.dp)
                bottom.linkTo(enter.bottom)
            }
        ) {
            if(STAT_COMMUNICATION){
                bleViewModel.writeRXCharacteristic(
                    multiplePermissionState = multiplePermissionsState,
                    context = context,
                    value = LEFT.toString().toByteArray()
                )
            }
            else{
                mqttViewModel.publish(topic = TOPIC_PUBLISH, data = LEFT.toString())
            }
//            Toast.makeText(context, "Power", Toast.LENGTH_SHORT).show()
        }

        ButtonView(
            descriptionContent = "Right",
            buttonIcon = true,
            title = "Right",
            enable = true,
            icon = drawable.ic_baseline_arrow_right_48,
            roundedShape = 100,
            tintColor = MaterialTheme.colorScheme.onPrimary,
            modifier = modifier.constrainAs(right){
                top.linkTo(enter.top)
                start.linkTo(enter.end, 18.dp)
                bottom.linkTo(enter.bottom)
            }
        ) {
            if(STAT_COMMUNICATION){
                bleViewModel.writeRXCharacteristic(
                    multiplePermissionState = multiplePermissionsState,
                    context = context,
                    value = RIGHT.toString().toByteArray()
                )
            }
            else{
                mqttViewModel.publish(topic = TOPIC_PUBLISH, data = RIGHT.toString())
            }
//            Toast.makeText(context, "Power", Toast.LENGTH_SHORT).show()
        }

        ButtonView(
            descriptionContent = "Menu",
            buttonIcon = false,
            title = "Menu",
            enable = true,
            width = 92,
            roundedShape = 100,
            modifier = modifier.constrainAs(menu){
                start.linkTo(parent.start)
                top.linkTo(down.top)
                bottom.linkTo(down.bottom)
            }
        ) {
            if(STAT_COMMUNICATION){
                bleViewModel.writeRXCharacteristic(
                    multiplePermissionState = multiplePermissionsState,
                    context = context,
                    value = MENU.toString().toByteArray()
                )
            }
            else{
                mqttViewModel.publish(topic = TOPIC_PUBLISH, data = MENU.toString())
            }
//            remoteViewModel.insertCommand(command = NO_COMMAND)
//            Toast.makeText(context, "Source", Toast.LENGTH_SHORT).show()
        }

        ButtonView(
            descriptionContent = "Exit",
            buttonIcon = false,
            title = "Exit",
            enable = true,
            width = 92,
            roundedShape = 100,
            modifier = modifier.constrainAs(exit){
                end.linkTo(parent.end)
                top.linkTo(down.top)
                bottom.linkTo(down.bottom)
            }
        ) {
            if(STAT_COMMUNICATION){
                bleViewModel.writeRXCharacteristic(
                    multiplePermissionState = multiplePermissionsState,
                    context = context,
                    value = EXIT.toString().toByteArray()
                )
            }
            else{
                mqttViewModel.publish(topic = TOPIC_PUBLISH, data = EXIT.toString())
            }
//            remoteViewModel.insertCommand(command = NO_COMMAND)
//            Toast.makeText(context, "Source", Toast.LENGTH_SHORT).show()
        }
    }

}