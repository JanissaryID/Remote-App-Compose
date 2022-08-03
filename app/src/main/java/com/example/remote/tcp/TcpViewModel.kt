package com.example.remote.tcp

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.remote.ID_TV
import com.example.remote.IS_DIALOG_OPEN
import com.example.remote.proto.ProtoViewModel
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.InetAddress
import java.net.Socket
import java.nio.CharBuffer


class TcpViewModel: ViewModel() {

    val IPAddress = "192.168.4.22"
    val Port = 80

    private lateinit var client: Socket

    var DataTcp: String by mutableStateOf("")

    private lateinit var uiScope : CoroutineScope

    fun Connect(){
        try {
            client = Socket(InetAddress.getByName(IPAddress), Port)
//            client.connect(InetSocketAddress(IPAddress, Port), 5)

            Log.i("info", "TCP Status ${client.isConnected}")
        }
        catch (e : Exception){
            Log.i("info", "ERROR $e")
        }
    }

    suspend fun TcpSend(ssid: String, password: String, protoViewModel: ProtoViewModel){
        ID_TV = ""

        try {
            val client = Socket(IPAddress, Port)

            val output = PrintWriter(client.getOutputStream(), true)
            val input = BufferedReader(InputStreamReader(client.inputStream))

            Log.d("debug", "Connected")
            output.println("#$ssid#$password#;")
            Log.d("debug", "Send To TV = #$ssid#$password#;")

//            val cb: CharBuffer = CharBuffer.allocate(15)


            for(i in 1..15){
                Log.d("debug", "index = $i")
                DataTcp += input.read().toChar()
            }

            Log.d("debug", "Data = ${DataTcp}")
            Log.d("debug", "Data = ${DataTcp.length}")

            ID_TV = DataTcp.toString()

            client.close()
            if(!ID_TV.isNullOrEmpty()){
                protoViewModel.updateData(idTv = ID_TV)
                IS_DIALOG_OPEN.value = false
            }
//            uiScope.cancel()
        }
        catch (e : Exception){
            Log.i("info", "ERROR $e")
        }
    }

    fun cancelCoroutine(){
        uiScope?.cancel()
    }

    fun coroutineBack(ssid: String, password: String){
//        var viewModelJob = Job()
        try
        {
            uiScope = CoroutineScope(SupervisorJob())

            uiScope.launch {
                withContext(Dispatchers.IO) {
//                    TcpSend(ssid = ssid, password = password)
                }
            }
        }
        catch (ex: Exception) {
            System.err.println("Exception subscribing")
            ex.printStackTrace()
        }
    }
}