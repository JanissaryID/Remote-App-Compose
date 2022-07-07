package com.example.remote.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.remote.*
import com.example.remote.api.RemoteViewModel
import com.example.remote.component.ButtonView

@Composable
fun ScreenInformation(remoteViewModel: RemoteViewModel = RemoteViewModel()) {
    val modifier = Modifier

    val fontSize: TextUnit = MaterialTheme.typography.titleMedium.fontSize

    ConstraintLayout(modifier = modifier
        .fillMaxSize()) {
        val (
            channelName,
            frequency,
            postCode,
            lifeTime,

            pictureMode,
            soundMode,
            vchannelName,
            vfrequency,
            vpostCode,
            vlifeTime,
            vpictureMode,
            vsoundMode,
        ) = createRefs()

        val (
            projectType,
            vprojectType,
            mac,
            vmac,
            softwareVersion,
            vsoftwareVersion,
            vsoftwareVersionlast,
        ) = createRefs()

        Text(
            text = "Project Name",
            fontWeight = FontWeight.SemiBold,
            fontSize = fontSize,
            modifier = modifier.constrainAs(projectType){
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            }
        )

        Text(
            text = ": $PROJECT_NAME",
            fontWeight = FontWeight.SemiBold,
            fontSize = fontSize,
            modifier = modifier.constrainAs(vprojectType){
                top.linkTo(parent.top)
                start.linkTo(softwareVersion.end)
            }
        )

        Text(
            text = "Software Version",
            fontWeight = FontWeight.SemiBold,
            fontSize = fontSize,
            modifier = modifier.constrainAs(softwareVersion){
                top.linkTo(projectType.bottom, 16.dp)
                start.linkTo(parent.start)
            }
        )

        Text(
            text = ": $SW_VERSION",
            fontWeight = FontWeight.SemiBold,
            fontSize = fontSize,
            modifier = modifier.constrainAs(vsoftwareVersion){
                top.linkTo(vprojectType.bottom, 16.dp)
                start.linkTo(softwareVersion.end)
            }
        )

        Text(
            text = "  $SW_VERSION_LAST",
            fontWeight = FontWeight.SemiBold,
            fontSize = fontSize,
            modifier = modifier.constrainAs(vsoftwareVersionlast){
                top.linkTo(vsoftwareVersion.bottom)
                start.linkTo(softwareVersion.end)
            }
        )

        Text(
            text = "Mac Address",
            fontWeight = FontWeight.SemiBold,
            fontSize = fontSize,
            modifier = modifier.constrainAs(mac){
                top.linkTo(vsoftwareVersionlast.bottom, 16.dp)
                start.linkTo(parent.start)
            }
        )

        Text(
            text = ": $MAC_TV",
            fontWeight = FontWeight.SemiBold,
            fontSize = fontSize,
            modifier = modifier.constrainAs(vmac){
                top.linkTo(vsoftwareVersionlast.bottom, 16.dp)
                start.linkTo(softwareVersion.end)
            }
        )

        Text(
            text = "Life Time",
            fontWeight = FontWeight.SemiBold,
            fontSize = fontSize,
            modifier = modifier.constrainAs(lifeTime){
                top.linkTo(mac.bottom, 16.dp)
                start.linkTo(parent.start)
            }
        )

        Text(
            text = ": $TIME",
            fontWeight = FontWeight.SemiBold,
            fontSize = fontSize,
            modifier = modifier.constrainAs(vlifeTime){
                top.linkTo(vmac.bottom, 16.dp)
                start.linkTo(softwareVersion.end)
            }
        )

        Text(
            text = "Channel Name",
            fontWeight = FontWeight.SemiBold,
            fontSize = fontSize,
            modifier = modifier.constrainAs(channelName){
                top.linkTo(lifeTime.bottom, 16.dp)
                start.linkTo(parent.start)
            }
        )

        Text(
            text = ": $CHANNEL_TV",
            fontWeight = FontWeight.SemiBold,
            fontSize = fontSize,
            modifier = modifier.constrainAs(vchannelName){
                top.linkTo(vlifeTime.bottom, 16.dp)
                start.linkTo(softwareVersion.end)
            }
        )

        Text(
            text = "Frequency",
            fontWeight = FontWeight.SemiBold,
            fontSize = fontSize,
            modifier = modifier.constrainAs(frequency){
                top.linkTo(channelName.bottom, 16.dp)
                start.linkTo(parent.start)
            }
        )

        Text(
            text = ": $FREQUENCY",
            fontWeight = FontWeight.SemiBold,
            fontSize = fontSize,
            modifier = modifier.constrainAs(vfrequency){
                top.linkTo(vchannelName.bottom, 16.dp)
                start.linkTo(softwareVersion.end)
            }
        )

        Text(
            text = "Post Code",
            fontWeight = FontWeight.SemiBold,
            fontSize = fontSize,
            modifier = modifier.constrainAs(postCode){
                top.linkTo(frequency.bottom, 16.dp)
                start.linkTo(parent.start)
            }
        )

        Text(
            text = ": $POST",
            fontWeight = FontWeight.SemiBold,
            fontSize = fontSize,
            modifier = modifier.constrainAs(vpostCode){
                top.linkTo(vfrequency.bottom, 16.dp)
                start.linkTo(softwareVersion.end)
            }
        )

        Text(
            text = "Picture Mode",
            fontWeight = FontWeight.SemiBold,
            fontSize = fontSize,
            modifier = modifier.constrainAs(pictureMode){
                top.linkTo(postCode.bottom, 16.dp)
                start.linkTo(parent.start)
            }
        )

        Text(
            text = ": $PICTURE_MODE",
            fontWeight = FontWeight.SemiBold,
            fontSize = fontSize,
            modifier = modifier.constrainAs(vpictureMode){
                top.linkTo(vpostCode.bottom, 16.dp)
                start.linkTo(softwareVersion.end)
            }
        )

        Text(
            text = "Sound Mode",
            fontWeight = FontWeight.SemiBold,
            fontSize = fontSize,
            modifier = modifier.constrainAs(soundMode){
                top.linkTo(pictureMode.bottom, 16.dp)
                start.linkTo(parent.start)
            }
        )

        Text(
            text = ": $SOUND_MODE",
            fontWeight = FontWeight.SemiBold,
            fontSize = fontSize,
            modifier = modifier.constrainAs(vsoundMode){
                top.linkTo(pictureMode.bottom, 16.dp)
                start.linkTo(softwareVersion.end)
            }
        )
    }
}