package com.example.remotev2.componen

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.remotev2.ID_TV
import com.example.remotev2.IS_DIALOG_OPEN
import com.example.remotev2.proto.ProtoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogId(protoViewModel: ProtoViewModel) {
    val context = LocalContext.current

    var text_id by remember { mutableStateOf(TextFieldValue(ID_TV)) }
//    var text_password by remember { mutableStateOf(TextFieldValue("")) }

    Dialog(onDismissRequest = { IS_DIALOG_OPEN.value = true }) {
        Card(modifier = Modifier
            .wrapContentHeight()
            .padding(8.dp),
            containerColor = Color.White
//            shape = RoundedCornerShape(10),
        ) {
            ConstraintLayout(modifier = Modifier.wrapContentHeight().padding(16.dp)) {

                val (ssid, save, exit) = createRefs()

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
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                        textColor = Color.Black,
                        cursorColor = MaterialTheme.colorScheme.primary
                    ),
                    value = text_id,
                    label = { Text(text = "ID Televisi") },
                    onValueChange = {
                        text_id = it
                    }
                )

                ButtonView2(color = MaterialTheme.colorScheme.primary ,title = "Save", Modifier.constrainAs(save) {
                    top.linkTo(ssid.bottom, 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }, enable = true){

                    Toast.makeText(context, "${text_id.text}", Toast.LENGTH_SHORT).show()

//                    CoroutineScope(Dispatchers.Default).launch { tcpViewModel.TcpSend(ssid = text_ssid.text, password = text_password.text, protoViewModel = protoViewModel) }
                    protoViewModel.updateData(idTv = text_id.text)
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