package com.example.remote_ble.componen

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.remote_ble.*
import com.example.remote_ble.ble.BleViewModel
import com.example.remote_ble.mqtt.MqttViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ButtonGroup(
    mqttViewModel: MqttViewModel,
    iconTop: Int,
    iconBottom: Int,
    modifier: Modifier,
    bleViewModel: BleViewModel,
    multiplePermissionsState: MultiplePermissionsState,
    context: Context,
    typeButton: Boolean, // if false is volume and if true is ch
    title: String) {
    Surface(
        shape = RoundedCornerShape(100.dp),
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val (chPlus, chMinus, titleButtonGroup) = createRefs()
            val modifier = Modifier
            ButtonView(
                descriptionContent = "Chanel Plus",
                buttonIcon = true,
                title = "Chanel Plus",
                enable = true,
                tintColor = MaterialTheme.colorScheme.onPrimary,
                icon = iconTop,
                roundedShape = 100,
                modifier = modifier.constrainAs(chPlus) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
            ) {
                if (!typeButton)
                    if(STAT_COMMUNICATION){
                        bleViewModel.writeRXCharacteristic(
                            multiplePermissionState = multiplePermissionsState,
                            context = context,
                            value = VOL_PLUS.toString().toByteArray()
                        )
                    }
                    else{
                        mqttViewModel.publish(topic = TOPIC_PUBLISH, data = VOL_PLUS.toString())
                    }
                else
                    if(STAT_COMMUNICATION){
                        bleViewModel.writeRXCharacteristic(
                            multiplePermissionState = multiplePermissionsState,
                            context = context,
                            value = CH_PLUS.toString().toByteArray()
                        )
                    }
                    else{
                        mqttViewModel.publish(topic = TOPIC_PUBLISH, data = CH_PLUS.toString())
                    }
            }

            Text(
                text = title,
                fontWeight = FontWeight.SemiBold,
                fontSize = MaterialTheme.typography.labelMedium.fontSize,
                modifier = modifier.constrainAs(titleButtonGroup) {
                    bottom.linkTo(chMinus.top)
                    top.linkTo(chPlus.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })

            ButtonView(
                descriptionContent = "Chanel Min",
                buttonIcon = true,
                title = "Chanel Min",
                enable = true,
                tintColor = MaterialTheme.colorScheme.onPrimary,
                icon = iconBottom,
                roundedShape = 100,
                modifier = modifier.constrainAs(chMinus) {
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                }
            ) {
                if (!typeButton)
                    if(STAT_COMMUNICATION){
                        bleViewModel.writeRXCharacteristic(
                            multiplePermissionState = multiplePermissionsState,
                            context = context,
                            value = VOL_MINUS.toString().toByteArray()
                        )
                    }
                    else{
                        mqttViewModel.publish(topic = TOPIC_PUBLISH, data = VOL_MINUS.toString())
                    }
                else
                    if(STAT_COMMUNICATION){
                        bleViewModel.writeRXCharacteristic(
                            multiplePermissionState = multiplePermissionsState,
                            context = context,
                            value = CH_MINUS.toString().toByteArray()
                        )
                    }
                    else{
                        mqttViewModel.publish(topic = TOPIC_PUBLISH, data = CH_MINUS.toString())
                    }
//                    Toast.makeText(context, "Power", Toast.LENGTH_SHORT).show()
            }
        }
    }
}