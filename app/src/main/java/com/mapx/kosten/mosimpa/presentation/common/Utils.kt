package com.mapx.kosten.mosimpa.presentation.common

import android.content.Context
import android.net.wifi.WifiManager
import com.mapx.kosten.mosimpa.R
import com.mapx.kosten.mosimpa.domain.common.Constants
import com.mapx.kosten.mosimpa.domain.common.Constants.Companion.EMPTY_STRING
import com.mapx.kosten.mosimpa.domain.common.Constants.Companion.SENSOR_HEART_ID
import com.mapx.kosten.mosimpa.domain.common.Constants.Companion.SENSOR_O2_ID
import com.mapx.kosten.mosimpa.domain.common.Constants.Companion.SENSOR_BLOOD_ID
import com.mapx.kosten.mosimpa.domain.common.Constants.Companion.SENSOR_TEMPERATURE_ID
import com.mapx.kosten.mosimpa.domain.entites.AlarmsEntity

class Utils {

    companion object {
        const val INVALID_PATIENT_ID = -1L

        private const val SENSOR_O2_MIN_CRITICAL = 90F
        private const val SENSOR_HEART_MIN_CRITICAL = 60F
        private const val SENSOR_HEART_MAX_CRITICAL = 90F
        private const val SENSOR_TEMP_MAX_CRITICAL = 38F

        fun getSensorNameById(id: Int): String {
            return when(id) {
                SENSOR_O2_ID -> "Sat. Oxígeno"
                SENSOR_HEART_ID -> "Frec. Cardiaca"
                SENSOR_BLOOD_ID -> "Presión Sang."
                SENSOR_TEMPERATURE_ID -> "Temperatura"
                else -> EMPTY_STRING
            }
        }

        fun getSensorSufixByID(id: Int): String {
            return when(id) {
                SENSOR_O2_ID -> " %"
                SENSOR_HEART_ID -> " Lpm"
                SENSOR_BLOOD_ID -> ""
                SENSOR_TEMPERATURE_ID -> " °C"
                else -> EMPTY_STRING
            }
        }

        fun getSensorStringValue(id: Int, value: Float): String {
            return when(id) {
                SENSOR_O2_ID -> "%.1f".format(value) + " %"
                SENSOR_HEART_ID -> "%.0f".format(value) + " Lpm"
                SENSOR_BLOOD_ID -> "%.0f".format(value) + " mmHg"
                SENSOR_TEMPERATURE_ID -> "%.1f".format(value) + " °C"
                else -> EMPTY_STRING
            }
        }

        fun getSensorValueColorByID(id: Int, value: Float, min: Float, max: Float): Int {
            val colorOK = R.color.green
            val colorCritical = R.color.red
            return when(id) {
                SENSOR_O2_ID -> {
                    if (value <= min) {
                        colorCritical
                    } else {
                        colorOK
                    }
                }
                SENSOR_HEART_ID -> {
                    if (value < min || value > max ) {
                        colorCritical
                    } else {
                        colorOK
                    }
                }
                SENSOR_BLOOD_ID -> {
                    if (value < min || value > max ) {
                        colorCritical
                    } else {
                        colorOK
                    }
                }
                SENSOR_TEMPERATURE_ID -> {
                    if (value >= max) {
                        colorCritical
                    } else {
                        colorOK
                    }
                }
                else -> {
                    colorOK
                }
            }
        }

        fun getSensorMaxValueByID(id: Int): Int {
            return when(id) {
                SENSOR_O2_ID -> 100
                SENSOR_HEART_ID -> 200
                SENSOR_BLOOD_ID -> 50
                else -> 0
            }
        }

        fun getSensorMinValueByID(id: Int): Int {
            return when(id) {
                SENSOR_O2_ID -> 40
                SENSOR_HEART_ID -> 0
                SENSOR_BLOOD_ID -> 0
                else -> 0
            }
        }

        fun getMacAddress(context: Context?): String {
            var mac = Constants.DEFAULT_MAC_ADDRESS
            context?.let {
                val manager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
                val info = manager.connectionInfo
                mac = info.macAddress.toUpperCase()
            }
            return mac.replace(":","")
        }

    }

}