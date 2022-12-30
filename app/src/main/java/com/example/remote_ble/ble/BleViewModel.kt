package com.example.remote_ble.ble

import android.Manifest
import android.bluetooth.*
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Handler
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import com.example.remote_ble.*
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import java.util.*

class BleViewModel: ViewModel() {

    val MY_SERVICE_UUID = UUID.fromString("4fafc201-1fb5-459e-8fcc-c5c9c331914b")
    val MY_CHARACTERISTIC_UUID = UUID.fromString("beb5483e-36e1-4688-b7f5-ea07361b26a8")
    val CLIENT_CHARACTERISTICS_UUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb")

    val MY_SERVICE_UUID_UART = UUID.fromString("6E400001-B5A3-F393-E0A9-E50E24DCCA9E")
    val MY_CHARACTERISTIC_UUID_UART = UUID.fromString("6E400002-B5A3-F393-E0A9-E50E24DCCA9E")

    lateinit var bluetoothManager: BluetoothManager
    lateinit var bluetoothAdapter: BluetoothAdapter

    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    var ActivityApp : MainActivity? = null

    var context : Context? = null

    private val handler = Handler()

    var isScanning: Boolean by mutableStateOf(false)
    var isConnect: Boolean by mutableStateOf(false)

    private var myGatt: BluetoothGatt? = null

    fun createInstance(appCompatActivity: MainActivity){
        bluetoothManager = appCompatActivity.applicationContext.getSystemService(AppCompatActivity.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothAdapter = bluetoothManager.adapter

        activityResultLauncher = appCompatActivity.registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                Log.i("Bluetooth", ":request permission result ok")
            } else {
                Log.i("Bluetooth", ":request permission result canceled / denied")
            }
        }

        ActivityApp = appCompatActivity
        context = appCompatActivity
    }

    fun requestBluetoothPermission() : Boolean {

        var statPermission : Boolean = false

        if (bluetoothAdapter?.isEnabled == false) {
            Log.i("CheckBluetooth", ":Bluetooth Off Condition Wanna Turn On?")
            val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            activityResultLauncher.launch(enableBluetoothIntent)

            statPermission = false
        }
        else{
            Log.i("CheckBluetooth", ":Bluetooth On Condition")
            statPermission = true
//            STAT_BLUETOOTH = true
        }
        return statPermission
    }

    fun checkBluetoothCompatible(){
        if (bluetoothAdapter == null) {
            // Device doesn't support Bluetooth
            Log.i("CheckBluetooth", ":Device not Supported BLuetooth")
        }
        else{
            Log.i("CheckBluetooth", ":Device Supported BLuetooth")
        }
    }

    fun connectBluetooth(){

    }

    @OptIn(ExperimentalPermissionsApi::class)
    fun ScanBLEDevice(context: Context, multiplePermissionState: MultiplePermissionsState){
        val bluetoothLeScanner = bluetoothAdapter.bluetoothLeScanner
        if(!isScanning){
            handler.postDelayed({
                isScanning = false
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.BLUETOOTH_SCAN
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    multiplePermissionState.launchMultiplePermissionRequest()
                }
                bluetoothLeScanner.stopScan(leScanCallback)
            }, 10000)
            isScanning = true
            bluetoothLeScanner.startScan(leScanCallback)
        }
        else{
            isScanning = false
            bluetoothLeScanner.stopScan(leScanCallback)
        }
    }

    private val leDeviceListAdapter = LeDeviceListAdapter()

    inner class LeDeviceListAdapter {
        var devices: java.util.ArrayList<BluetoothDevice> by mutableStateOf(arrayListOf())

        fun addDevice(device: BluetoothDevice){
            if (!devices.contains(device)){
                devices.add(device)
            }
        }

        fun notifyDataSetChanged(){

        }
    }

    @OptIn(ExperimentalPermissionsApi::class)
    fun ShowBLEDevice(context: Context, multiplePermissionState: MultiplePermissionsState){
        val device = leDeviceListAdapter.devices
        device.forEach{ BLEDevice ->
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.BLUETOOTH_CONNECT
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                multiplePermissionState.launchMultiplePermissionRequest()
                return
            }
            Log.i("Bluetooth_device", "Bluetooth Device : ${BLEDevice.name} -- ${BLEDevice.address}")
        }
    }

    // Device scan callback.
    private val leScanCallback: ScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            super.onScanResult(callbackType, result)
            leDeviceListAdapter.addDevice(result.device)
            leDeviceListAdapter.notifyDataSetChanged()
        }
    }

    @OptIn(ExperimentalPermissionsApi::class)
    fun connectToGattServer(context: Context, macAddress: String, multiplePermissionState: MultiplePermissionsState) {
        // Get the BluetoothDevice object for the GATT server you want to connect to using the MAC address.
        val device: BluetoothDevice = bluetoothAdapter?.getRemoteDevice(macAddress)

        val gattCallback = object : BluetoothGattCallback() {
            override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
                if (newState == BluetoothProfile.STATE_CONNECTED) {
                    // The connection was established. You can now discover services on the GATT server.
                    if (ActivityCompat.checkSelfPermission(
                            context,
                            Manifest.permission.BLUETOOTH_CONNECT
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        multiplePermissionState.launchMultiplePermissionRequest()
                        return
                    }
                    gatt.discoverServices()
                }
            }

            override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
                if (status == BluetoothGatt.GATT_SUCCESS) {
                    // Services were discovered. You can now get a list of services and iterate through them
                    // to find the one you're interested in.
                    val services: List<BluetoothGattService> = gatt.services
                    for (service in services) {
                        if (service.uuid == MY_SERVICE_UUID) {
                            // This is the service you're looking for. You can now get a list of characteristics
                            // and iterate through them to find the one you want to read.
                            val characteristics: List<BluetoothGattCharacteristic> = service.characteristics
                            for (characteristic in characteristics) {
                                if (characteristic.uuid == MY_CHARACTERISTIC_UUID) {
                                    // This is the characteristic you want to read. You can now read the value
                                    // using the readCharacteristic() method.
                                    if (ActivityCompat.checkSelfPermission(
                                            context,
                                            Manifest.permission.BLUETOOTH_CONNECT
                                        ) != PackageManager.PERMISSION_GRANTED
                                    ) {
                                        multiplePermissionState.launchMultiplePermissionRequest()
                                        return
                                    }
//                                    gatt.readCharacteristic(characteristic)
                                    gatt.setCharacteristicNotification(characteristic, true)
                                    if (MY_CHARACTERISTIC_UUID == characteristic.uuid) {
                                        val descriptor = characteristic.getDescriptor(CLIENT_CHARACTERISTICS_UUID)
                                        Log.i("Gatt", "Get Gatt ${descriptor}")
                                        descriptor.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
                                        gatt.writeDescriptor(descriptor)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            override fun onCharacteristicRead(gatt: BluetoothGatt, characteristic: BluetoothGattCharacteristic, status: Int) {
                if (status == BluetoothGatt.GATT_SUCCESS) {
                    // The characteristic was read successfully. You can now get the value from the characteristic.
                    val value: ByteArray = characteristic.value
                    // Do something with the value.
                    Log.i("Bluetooth_device", "Value Read = ${characteristic?.getStringValue(0)}")
                }
            }

            override fun onCharacteristicChanged(
                gatt: BluetoothGatt?,
                characteristic: BluetoothGattCharacteristic?,
            ) {
                super.onCharacteristicChanged(gatt, characteristic)
                // The characteristic was read successfully. You can now get the value from the characteristic.
                val value: ByteArray = characteristic!!.value
                // Do something with the value.
//                Log.i("Bluetooth_device", "Value Notify = ${String(value)}")
                ExtractString(String(value));
//                Log.i("Bluetooth_device", "Value Notify ${characteristic?.getStringValue(0)}")
            }
        }

        // Connect to the GATT server using the connectGatt() method.
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.BLUETOOTH_CONNECT
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            multiplePermissionState.launchMultiplePermissionRequest()
            return
        }

        if(isConnect){
            myGatt = device.connectGatt(context, false, gattCallback)
        }
        else{
            myGatt!!.disconnect()
            myGatt!!.close()
            myGatt = null
        }
    }

    @OptIn(ExperimentalPermissionsApi::class)
    fun writeRXCharacteristic(value: ByteArray?, context: Context, multiplePermissionState: MultiplePermissionsState) {
        val RxService: BluetoothGattService = myGatt!!.getService(MY_SERVICE_UUID_UART)
        val RxChar = RxService.getCharacteristic(MY_CHARACTERISTIC_UUID_UART)

        RxChar.value = value
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.BLUETOOTH_CONNECT
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            multiplePermissionState.launchMultiplePermissionRequest()
            return
        }
        myGatt!!.writeCharacteristic(RxChar)
    }

    fun ExtractString(myDataTv: String){
        var splitData = myDataTv.split('{', '}', '\"', '#')

//        Log.i("Bluetooth_device", "Split Data = ${splitData[0]}")

        when(splitData[0]){
            "a" -> FREQUENCY = splitData[1]
            "b" -> POST = splitData[1]
            "c" -> TIME = splitData[1]
            "d" -> picMode(splitData[1])
            "e" -> soundMode(splitData[1])
            "f" -> SW_VERSION1 = splitData[1]
            "g" -> SW_VERSION2 = splitData[1]
            "h" -> SW_VERSION_LAST = splitData[1]
            "i" -> CHANNEL_TV = splitData[1]
            "j" -> PROJECT_NAME = splitData[1]
            "k" -> MAC_TV = splitData[1]
        }

        SW_VERSION = "$SW_VERSION1-$SW_VERSION2"
    }

    @RequiresApi(Build.VERSION_CODES.R)
    @OptIn(ExperimentalPermissionsApi::class)
    fun showPairedDevice(context: Context, multiplePermissionState: MultiplePermissionsState){

        val pairedDevices = if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.BLUETOOTH_CONNECT
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            multiplePermissionState.launchMultiplePermissionRequest()
            return
        }
        else
            bluetoothAdapter.bondedDevices

        if (pairedDevices.isNotEmpty()) {
            // Show a list of paired devices here
            for (device in pairedDevices) {
                Log.i("Bluetooth_device", "${device.name} -- ${device.type} -- ${device.uuids.size}")
            }
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