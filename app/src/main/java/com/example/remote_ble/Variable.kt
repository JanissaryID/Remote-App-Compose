package com.example.remote_ble

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

const val POLYTRON_CLIENT_USER_NAME = "krisna_rw"
const val POLYTRON_CLIENT_PASSWORD = "krisna-software"
const val POLYTRON_MQTT_HOST = "tcp://broker-dev.iot.polytron.angkasa.id:9913"

// Other options
const val POLYTRON_CONNECTION_TIMEOUT = 3
const val POLYTRON_CONNECTION_KEEP_ALIVE_INTERVAL = 60
const val POLYTRON_CONNECTION_CLEAN_SESSION = true
const val POLYTRON_CONNECTION_RECONNECT = true

const val TOPIC = "test/monitor"
const val TOPIC_PUBLISH = "/test/remote"

var STAT_SCREEN: Boolean by mutableStateOf(false)

var FREQUENCY: String by mutableStateOf("")
var POST: String by mutableStateOf("")
var PICTURE_MODE: String by mutableStateOf("")
var SOUND_MODE: String by mutableStateOf("")
var SW_VERSION: String by mutableStateOf("")
var SW_VERSION_LAST: String by mutableStateOf("")
var TIME: String by mutableStateOf("")
var CHANNEL_TV: String by mutableStateOf("")
var MAC_TV: String by mutableStateOf("")
var PROJECT_NAME: String by mutableStateOf("")

var ID_TV: String by mutableStateOf("")

var IS_DIALOG_OPEN =  mutableStateOf(false)

const val POWER = 0x76
const val SOURCE = 0x75
const val MENU = 0x73
const val EXIT = 0xB8

const val VOL_PLUS = 0xC2
const val VOL_MINUS = 0xC3
const val FAV = 0xD2
const val LEFT = 0x72
const val UP = 0xD7
const val DOWN = 0xC7
const val RIGHT = 0x74

const val CH_PLUS = 0xC4
const val CH_MINUS = 0xC5
const val SELECT = 0x96
const val MUTE = 0xC1
const val ZOOM = 0xB6