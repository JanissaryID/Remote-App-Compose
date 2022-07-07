package com.example.remote.api

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.remote.*
import com.example.remote.api.Information.InformationApp
import com.example.remote.api.Information.InformationModel
import com.example.remote.api.Remote.M2mCin
import com.example.remote.api.Remote.RemoteApp
import com.example.remote.api.Remote.RemoteModel
import kotlinx.coroutines.*
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteViewModel: ViewModel() {

    private lateinit var uiScope : CoroutineScope

    fun insertCommand(command: Int){

        val bodyUpdate = RemoteModel(
            m2mCin = M2mCin(con = "{\"Key\":$command}")
        )

        RemoteApp.CreateInstance().insertCommand(bodyUpdate).enqueue(object :
            Callback<RemoteModel> {
            override fun onResponse(call: Call<RemoteModel>, response: Response<RemoteModel>) {
//                Log.d("debug", "Response Debug ${response}")
//                Log.d("debug", "Response Debug ${response.body()}")
            }

            override fun onFailure(call: Call<RemoteModel>, t: Throwable) {
                Log.d("error", t.message.toString())
                if (t.message == t.message){

//                    BtnOnMachine.isEnabled =true

//                    Toast.makeText(requireContext(), "Tidak ada koneksi Internet" , Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    fun getInformation(){
        try {
            InformationApp.CreateInstance().getData().enqueue(object :
                Callback<InformationModel> {
                override fun onResponse(call: Call<InformationModel>, response: Response<InformationModel>) {
//                    Log.i("data", "Code : ${response.code()}")
//                    Log.d("debug", "Code Machine Get : ${response.code()}")
//                    stateMachine = 0
                    if(response.code() == 200){
                        response.body()?.let {
                            val infoData = response.body()!!.m2mCin?.con
                            val parts = infoData?.subSequence(1, infoData?.length-1).toString()
                            val splitString = parts.split(':')
                            val splitComa = parts.split(',')
                            val splitStringComaSw1 = splitComa[1].split(':')
                            val splitStringComaSw2 = splitComa[2].split(':')
                            val splitStringComaSw3 = splitComa[3].split(':')
                            val splitStringChannel = splitComa[4].split(':')
                            val splitStringProject = splitComa[5].split(':')
                            val splitStringMac = splitComa[6].split(':')
                            val parts2 = splitString[1].subSequence(1, splitString[1]?.length-1).toString()
                            val data = parts2.split('#')
                            if (data[0].length > 0){
//                                Log.d("debug", "Data : ${splitStringMac}")
//                                val turnoffData = data[0].length
                                if(data[0].length != 6){
                                    FREQUENCY = "Tv Turn off"
                                    POST = "Tv Turn off"
                                    picMode("777")
                                    soundMode("777")
                                    TIME = "Tv Turn off"
                                    SW_VERSION = "Tv Turn off"
                                    SW_VERSION_LAST = ""
                                    CHANNEL_TV = "Tv Turn off"
                                    PROJECT_NAME = "Tv Turn off"
                                    MAC_TV = "Tv Turn off"
                                }
                                else{
                                    FREQUENCY = data[0].subSequence(0, data[0]?.length-3).toString()+ "." + data[0].subSequence(3, data[0]?.length-1).toString()
                                    POST = data[1].toString()
                                    picMode(data[2])
                                    soundMode(data[3])
                                    TIME = "${data[4]} Hours"
                                    SW_VERSION = "${splitStringComaSw1[1]?.subSequence(1, splitStringComaSw1[1]?.length-1)}" +
                                            "-" +
                                            "${splitStringComaSw2[1]?.subSequence(1, splitStringComaSw2[1]?.length-1)}" +
                                            " -- "
                                    SW_VERSION_LAST = "${splitStringComaSw3[1]?.subSequence(1, splitStringComaSw3[1]?.length-1)}"
                                    CHANNEL_TV = "${splitStringChannel[1]?.subSequence(1, splitStringChannel[1]?.length-1)}"
                                    PROJECT_NAME = "${splitStringProject[1]?.subSequence(1, splitStringProject[1]?.length-1)}"
                                    MAC_TV = "${splitStringMac[1]?.subSequence(1, splitStringMac[1]?.length)}:${splitStringMac[2]}:${splitStringMac[3]}:${splitStringMac[4]}:${splitStringMac[5]}:${splitStringMac[6]?.subSequence(0, splitStringMac[1]?.length-1)}"
                                }
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<InformationModel>, t: Throwable) {
                    Log.i("info", "Fail get Data ${t.message.toString()}")
                    if (t.message == t.message){
                        Log.i("info", "Failed")
                    }
                }
            })
        }
        catch (e : Exception){
//            errorMessage = e.message.toString()
            Log.i("info", "ERROR $e")
//            Toast.makeText(requireContext(), "Error $e" , Toast.LENGTH_SHORT).show()
        }
    }

    fun picMode(channel: String){
        when(channel){
            "74" -> PICTURE_MODE = "Standar"
            "85" -> PICTURE_MODE = "Pengguna"
            "1617" -> PICTURE_MODE = "DIPE"
            "1618" -> PICTURE_MODE = "Alami"
            "1619" -> PICTURE_MODE = "Standar Lembut"
            "1620" -> PICTURE_MODE = "Lembut Alami"
            "1621" -> PICTURE_MODE = "Sinema"
            "1622" -> PICTURE_MODE = "Aksi"
            "777" -> PICTURE_MODE = "Tv Turn off"
        }
    }

    fun soundMode(channel: String){
        when(channel){
            "510" -> SOUND_MODE = "Standar"
            "511" -> SOUND_MODE = "Musik"
            "512" -> SOUND_MODE = "Berita"
            "513" -> SOUND_MODE = "Olah Raga"
            "514" -> SOUND_MODE = "Pengguna"
            "777" -> SOUND_MODE = "Tv Turn off"
        }
    }

    fun coroutineBack(){
        var viewModelJob = Job()
        try
        {
            uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

            uiScope.launch {
                withContext(Dispatchers.IO) {
                    while (true){
                        getInformation()
                        delay(100L)
//                        Log.d("debug", "Get")
                    }
                }
            }
        }
        catch (ex: Exception) {
            System.err.println("Exception subscribing")
            ex.printStackTrace()
        }


    }
}