package com.example.remote.mqtt

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.ViewModel
import com.example.remote.R
import info.mqtt.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import java.util.*


class MQTTClientViewModel: ViewModel() {

//    val host  = "mqtt.antares.id:1883"
    private var uniqueID:String? = null
    private val PREF_UNIQUE_ID = "PREF_UNIQUE_ID"

    lateinit var mqttAndroidClient: MqttAndroidClient

//    lateinit var foregroundNotification : Notification
//    lateinit var foregroundNotification: NotificationManager

    @RequiresApi(Build.VERSION_CODES.O)
    fun create(context: Context, notification: Notification){
        try
        {
            mqttAndroidClient =  MqttAndroidClient(context, "tcp://mqtt.antares.id:1883", "MQTTSample" + id(context = context)).apply { setForegroundService(notification = notification, 0) }
            connect()
            mqttAndroidClient.setCallback(object: MqttCallbackExtended {
                override fun connectComplete(b:Boolean, s:String) {
                    Log.w("mqtt", "Complete " + s)
//                    connect(context, notification)
//                uiUpdater?.resetUIWithConnection(true)
                }
                override fun connectionLost(throwable:Throwable) {
//                uiUpdater?.resetUIWithConnection(false)
                }
                override fun messageArrived(topic:String, mqttMessage: MqttMessage) {
                    Log.w("Mqtt", "Message " + mqttMessage.toString())
//                uiUpdater?.update(mqttMessage.toString())
                }
                override fun deliveryComplete(iMqttDeliveryToken: IMqttDeliveryToken) {
//                    connect(context, notification)
                }
            })
        }
        catch (ex:MqttException) {
            System.err.println("Exception subscribing")
            ex.printStackTrace()
        }

    }

    fun connect(){
//        create(context = context, notification = notification)
        val mqttConnectOptions = MqttConnectOptions()
        mqttConnectOptions.setAutomaticReconnect(true)
        mqttConnectOptions.setCleanSession(false)
        mqttConnectOptions.setUserName("MQTTSample")
        mqttConnectOptions.setPassword("".toCharArray())
        try
        {
//            var params = connectionParams
            mqttAndroidClient.connect(mqttConnectOptions, null, object: IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken) {
                    val disconnectedBufferOptions = DisconnectedBufferOptions()
                    disconnectedBufferOptions.setBufferEnabled(true)
                    disconnectedBufferOptions.setBufferSize(100)
                    disconnectedBufferOptions.setPersistBuffer(false)
                    disconnectedBufferOptions.setDeleteOldestMessages(false)
                    mqttAndroidClient.setBufferOpts(disconnectedBufferOptions)
                    subscribe("/oneM2M/resp/antares-cse/e09945e5329c4890:641f9aa460637c0f/json")

                }
                override fun onFailure(asyncActionToken: IMqttToken, exception:Throwable) {
                    Log.w("Mqtt", "Failed to connect to: " + "tcp://mqtt.antares.id:1883" + exception.toString())
                }
            })
        }
        catch (ex: MqttException) {
            ex.printStackTrace()
        }
    }

    fun subscribe(topic: String){
        try
        {
            mqttAndroidClient.subscribe(topic, 0, null, object:IMqttActionListener {
                override fun onSuccess(asyncActionToken:IMqttToken) {
                    Log.w("Mqtt", "Subscription!")
//                    uiUpdater?.updateStatusViewWith("Subscribed to Topic")
                }
                override fun onFailure(asyncActionToken:IMqttToken, exception:Throwable) {
                    Log.w("Mqtt", "Subscription fail!")
//                    uiUpdater?.updateStatusViewWith("Falied to Subscribe to Topic")
                }
            })
        }
        catch (ex:MqttException) {
            System.err.println("Exception subscribing")
            ex.printStackTrace()
        }
    }

    @Synchronized fun id(context:Context):String {
        if (uniqueID == null)
        {
            val sharedPrefs = context.getSharedPreferences(
                PREF_UNIQUE_ID, Context.MODE_PRIVATE)
            uniqueID = sharedPrefs.getString(PREF_UNIQUE_ID, null)
            if (uniqueID == null)
            {
                uniqueID = UUID.randomUUID().toString()
                val editor = sharedPrefs.edit()
                editor.putString(PREF_UNIQUE_ID, uniqueID)
                editor.commit()
            }
        }
        return uniqueID!!
    }

}