package com.example.remotev2.componen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ButtonView2(color: Color = MaterialTheme.colorScheme.primary, title: String, modifier: Modifier = Modifier, enable: Boolean, onClick: () -> Unit ) {

    Button(onClick = { onClick() },
        modifier = modifier
            .height(56.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(40.dp),
        elevation = null,
        enabled = enable,
        colors = ButtonDefaults.buttonColors(color)
    ) {
        Text(text = title, fontWeight = FontWeight.SemiBold)
    }
}