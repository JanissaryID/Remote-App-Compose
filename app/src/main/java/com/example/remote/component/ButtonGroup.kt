package com.example.remote.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.remote.*
import com.example.remote.api.RemoteViewModel

@Composable
fun ButtonGroup(
    remoteViewModel: RemoteViewModel = RemoteViewModel(),
    iconTop: Int,
    iconBottom: Int,
    modifier: Modifier,
    typeButton: Boolean, // if false is volume and if true is ch
    title: String) {
    Surface(
        shape = RoundedCornerShape(100.dp),
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier
    ) {
        ConstraintLayout(modifier = Modifier
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
                modifier = modifier.constrainAs(chPlus){
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
            ) {
                if (!typeButton) remoteViewModel.insertCommand(command = VOL_PLUS) else remoteViewModel.insertCommand(command = CH_PLUS)
//                    Toast.makeText(context, "Power", Toast.LENGTH_SHORT).show()
            }

            Text(
                text = title,
                fontWeight = FontWeight.SemiBold,
                fontSize = MaterialTheme.typography.labelMedium.fontSize,
                modifier = modifier.constrainAs(titleButtonGroup){
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
                modifier = modifier.constrainAs(chMinus){
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                }
            ) {
                if (!typeButton) remoteViewModel.insertCommand(command = VOL_MINUS) else remoteViewModel.insertCommand(command = CH_MINUS)
//                    Toast.makeText(context, "Power", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //        Surface(
//            modifier = modifier
//                .width(72.dp)
//                .height(216.dp)
//                .constrainAs(channel) {
//                    top.linkTo(buttonPower.bottom, 72.dp)
//                    end.linkTo(parent.end)
//                },
//            shape = RoundedCornerShape(100.dp),
//            color = MaterialTheme.colorScheme.primary
//        ) {
//            ConstraintLayout(modifier = modifier
//                .fillMaxSize()
//            ) {
//                val (chPlus, chMin) = createRefs()
//                val modifier = Modifier
//
//                ButtonView(
//                    descriptionContent = "Chanel Plus",
//                    buttonIcon = true,
//                    title = "Chanel Plus",
//                    enable = true,
//                    tintColor = MaterialTheme.colorScheme.onPrimary,
//                    icon = R.drawable.ic_baseline_arrow_drop_up_48,
//                    roundedShape = 100,
//                    modifier = modifier.constrainAs(chPlus){
//                        top.linkTo(parent.top)
//                        end.linkTo(parent.end)
//                    }
//                ) {
////                    Toast.makeText(context, "Power", Toast.LENGTH_SHORT).show()
//                }
//
//                ButtonView(
//                    descriptionContent = "Chanel Min",
//                    buttonIcon = true,
//                    title = "Chanel Min",
//                    enable = true,
//                    tintColor = MaterialTheme.colorScheme.onPrimary,
//                    icon = R.drawable.ic_baseline_arrow_drop_down_48,
//                    roundedShape = 100,
//                    modifier = modifier.constrainAs(chMin){
//                        bottom.linkTo(parent.bottom)
//                        end.linkTo(parent.end)
//                    }
//                ) {
////                    Toast.makeText(context, "Power", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
}