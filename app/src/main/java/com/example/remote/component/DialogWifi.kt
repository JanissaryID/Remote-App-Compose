package com.example.remote.component

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.remote.ID_TV
import com.example.remote.IS_DIALOG_OPEN
import com.example.remote.proto.ProtoViewModel
import com.example.remote.tcp.TcpViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewDialogWifi(tcpViewModel: TcpViewModel = TcpViewModel(), protoViewModel: ProtoViewModel) {

    val context = LocalContext.current

    var text_ssid by remember { mutableStateOf(TextFieldValue("")) }
    var text_password by remember { mutableStateOf(TextFieldValue("")) }

    Dialog(onDismissRequest = { IS_DIALOG_OPEN.value = true }) {
        Card(modifier = Modifier
            .wrapContentHeight()
            .padding(8.dp),
            containerColor = Color.White
//            shape = RoundedCornerShape(10),
        ) {
            ConstraintLayout(modifier = Modifier.wrapContentHeight().padding(16.dp)) {

                val (ssid, password, save, exit) = createRefs()

                OutlinedTextField(
                    enabled = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(ssid) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.onSurface,
                        focusedLabelColor = MaterialTheme.colorScheme.onSurface,
                        textColor = MaterialTheme.colorScheme.onSurface,
                        cursorColor = MaterialTheme.colorScheme.onSurface
                    ),
                    value = text_ssid,
                    label = { Text(text = "SSID") },
                    onValueChange = {
                        text_ssid = it
                    }
                )

                OutlinedTextField(
                    enabled = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(password) {
                            top.linkTo(ssid.bottom, 8.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.onSurface,
                        focusedLabelColor = MaterialTheme.colorScheme.onSurface,
                        textColor = MaterialTheme.colorScheme.onSurface,
                        cursorColor = MaterialTheme.colorScheme.onSurface
                    ),
                    value = text_password,
                    label = { Text(text = "Password Wifi") },
                    onValueChange = {
                        text_password = it
                    }
                )

                ButtonView2(color = MaterialTheme.colorScheme.primary ,title = "Save", Modifier.constrainAs(save) {
                    top.linkTo(password.bottom, 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }, enable = true){

                    Toast.makeText(context, "${text_ssid.text} and ${text_password.text}", Toast.LENGTH_SHORT).show()

                    CoroutineScope(Dispatchers.Default).launch { tcpViewModel.TcpSend(ssid = text_ssid.text, password = text_password.text, protoViewModel = protoViewModel) }

                }

                ButtonView2(color = Color.Red,title = "Exit", Modifier.constrainAs(exit) {
                    top.linkTo(save.bottom, 8.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }, enable = true){
//                    tcpViewModel.TcpClose()
                    IS_DIALOG_OPEN.value = false

                }
            }
        }
    }
}