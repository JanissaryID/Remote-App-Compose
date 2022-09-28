package com.example.remotev2.mqtt

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.remotev2.*
import info.mqtt.android.service.Ack
import info.mqtt.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import org.json.JSONObject

class MqttViewModel: ViewModel() {

    private lateinit var mqttAndroidClient: MqttAndroidClient

    val serverUri = POLYTRON_MQTT_HOST
    private val clientId: String = MqttClient.generateClientId()

    var connectionStatus: Boolean by mutableStateOf(false)

    fun connect(applicationContext : Context) {

        val options = MqttConnectOptions()
        options.isAutomaticReconnect = POLYTRON_CONNECTION_RECONNECT
        options.isCleanSession = POLYTRON_CONNECTION_CLEAN_SESSION
        options.userName = POLYTRON_CLIENT_USER_NAME
        options.password = POLYTRON_CLIENT_PASSWORD.toCharArray()
        options.connectionTimeout = POLYTRON_CONNECTION_TIMEOUT
        options.keepAliveInterval = POLYTRON_CONNECTION_KEEP_ALIVE_INTERVAL

        mqttAndroidClient = MqttAndroidClient ( applicationContext, serverUri, clientId, Ack.AUTO_ACK )
        try {
            val token = mqttAndroidClient.connect(options)
            token.actionCallback = object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken)                        {
                    Log.i("Connection", "success ")
                    subscribe(TOPIC)
//                    publish(topic = TOPIC_PUBLISH, data = )
                    connectionStatus = true
                    // Give your callback on connection established here
                }
                override fun onFailure(asyncActionToken: IMqttToken, exception: Throwable) {
                    connectionStatus = false
                    Log.i("Connection", "failure $exception")
                    // Give your callback on connection failure here
                    exception.printStackTrace()
                }
            }
        } catch (e: MqttException) {
            // Give your callback on connection failure here
            e.printStackTrace()
        }
    }

    fun subscribe(topic: String) {
        val qos = 2 // Mention your qos value
        try {
            mqttAndroidClient.subscribe(topic, qos, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken) {
                    Log.i("Subscribe", "success ")
                    receiveMessages()
                    // Give your callback on Subscription here
                }
                override fun onFailure(
                    asyncActionToken: IMqttToken,
                    exception: Throwable
                ) {
                    Log.i("Subscribe", "Failure $exception ")
                    // Give your subscription failure callback here
                }
            })
        } catch (e: MqttException) {
            // Give your subscription failure callback here
        }
    }

    fun receiveMessages() {
        mqttAndroidClient.setCallback(object : MqttCallback {
            override fun connectionLost(cause: Throwable) {
                Log.i("Connection", "Lost $cause ")
                connectionStatus = false
            }
            override fun messageArrived(topic: String, message: MqttMessage) {
                try {
                    val data = String(message.payload, charset("UTF-8"))
                    Log.i("Message", "$data ")
                    extractData(data = data)
                } catch (e: Exception) {
                    Log.i("Message", "Error $e ")
                }
            }
            override fun deliveryComplete(token: IMqttDeliveryToken) {
                Log.i("Message", "Delivery Complete $token ")
            }
        })
    }

    fun publish(topic: String, data: String) {
        val encodedPayload : ByteArray
        try {

            val jsonData= JSONObject()
            jsonData.put("ID","$ID_TV")
            jsonData.put("Key","$data")

            encodedPayload = jsonData.toString().toByteArray(charset("UTF-8"))
            val message = MqttMessage(encodedPayload)
            message.qos = 2
            message.isRetained = false
            mqttAndroidClient.publish(topic, message)

            Log.i("Send", "Send $encodedPayload to $topic")
        } catch (e: Exception) {
            Log.i("error", "$e")
        } catch (e: MqttException) {
            Log.i("error", "Mqtt $e")
        }
    }

    fun extractData(data: String){

        try {
            var splitData = data.split('{', '}', '\"', '#')

            if(ID_TV == splitData[44]){
                FREQUENCY = splitData[4].subSequence(0, splitData[4]?.length-3).toString()+ "." + splitData[4].subSequence(3, splitData[4]?.length-1).toString()
                POST = splitData[5]
                picMode(splitData[6])
                soundMode(splitData[7])
                TIME = splitData[8]
                CHANNEL_TV = splitData[29]
                MAC_TV = splitData[39]
                PROJECT_NAME = splitData[34]
                SW_VERSION = splitData[14] + "-" + splitData[19]
                SW_VERSION_LAST = splitData[24]

                Log.i("Data", "$splitData ")
//                Log.i("Data", "${splitData[44]} ")
            }
        }
        catch (e : Exception){
            Log.i("info", "ERROR $e")
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
}