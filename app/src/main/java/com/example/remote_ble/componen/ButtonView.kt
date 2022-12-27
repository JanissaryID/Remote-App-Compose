package com.example.remote_ble.componen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.remote_ble.R

@Composable
fun ButtonView(
    roundedShape: Int,
    descriptionContent: String,
    buttonIcon: Boolean,
    icon: Int = R.drawable.ic_baseline_power_settings_new_48,
    title: String,
    modifier: Modifier = Modifier,
    enable: Boolean,
    width: Int = 72,
    heigh: Int = 72,
    color: Color = MaterialTheme.colorScheme.primary,
    tintColor: Color = MaterialTheme.colorScheme.primary,
    clicked: Boolean = false,
    powerButton: Boolean = false,
    onClick: () -> Unit
) {
    Button(onClick = { onClick() },
        modifier = modifier
            .height(heigh.dp)
            .width(width.dp),
//            .fillMaxWidth(),
        shape = RoundedCornerShape(roundedShape.dp),
        elevation = null,
        colors = if (!clicked) ButtonDefaults.buttonColors(color) else ButtonDefaults.buttonColors(Color.Transparent),
        enabled = enable,
        border = if (powerButton) BorderStroke(6.dp, MaterialTheme.colorScheme.errorContainer) else BorderStroke(6.dp, MaterialTheme.colorScheme.primary)
    ) {
        if(!buttonIcon) Text(text = title, fontWeight = FontWeight.SemiBold, fontSize = MaterialTheme.typography.labelMedium.fontSize) else Icon(tint = tintColor, painter = painterResource(icon), contentDescription = descriptionContent)
    }
}