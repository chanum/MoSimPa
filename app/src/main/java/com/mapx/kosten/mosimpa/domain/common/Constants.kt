package com.mapx.kosten.mosimpa.domain.common

class Constants {
    companion object{
        const val SERVER_URI = "tcp://192.168.0.83"

        const val MQTT_CONNECTION_OK = "connectComplete"
        const val MQTT_CONNECTION_FAIL = "connectLost"

        const val DEFAULT_MAC_ADDRESS = "AABBCCDDEEFF"

        const val SERVER_URI_PREFIX = "tcp://"

        const val EMPTY_STRING = ""

        const val SENSOR_O2_ID = 1
        const val SENSOR_HEART_ID = 2
        const val SENSOR_BLOOD_ID = 3
        const val SENSOR_TEMPERATURE_ID = 4

    }
}