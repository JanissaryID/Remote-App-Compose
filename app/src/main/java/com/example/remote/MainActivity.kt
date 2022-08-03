package com.example.remote

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.example.remote.api.RemoteViewModel
import com.example.remote.proto.ProtoViewModel
import com.example.remote.screens.ScreenBase
import com.example.remote.ui.theme.RemoteTheme

class MainActivity : ComponentActivity() {

    val remoteViewModel by viewModels<RemoteViewModel>()
    private lateinit var protoViewModel: ProtoViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        protoViewModel = ViewModelProvider(this).get(ProtoViewModel::class.java)
        protoViewModel.keyUrl.observe(this, {
            ID_TV = it.idtv
            Log.d("debug", "Id TV = $ID_TV")
        })

        setContent {
            RemoteTheme {
                ScreenBase(remoteViewModel = remoteViewModel, protoViewModel = protoViewModel)
                remoteViewModel.coroutineBack()
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RemoteTheme {
        Greeting("Android")
    }
}