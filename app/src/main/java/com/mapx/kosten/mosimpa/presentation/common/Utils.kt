package com.mapx.kosten.mosimpa.presentation.common

import com.mapx.kosten.mosimpa.domain.common.Constants.Companion.EMPTY_STRING
import com.mapx.kosten.mosimpa.domain.common.Constants.Companion.SENSOR_HEART_ID
import com.mapx.kosten.mosimpa.domain.common.Constants.Companion.SENSOR_O2_ID
import com.mapx.kosten.mosimpa.domain.common.Constants.Companion.SENSOR_BLOOD_ID

class Utils {

    companion object {
        const val INVALID_PATIENT_ID = -1L

        fun getSensorNameById(id: Int): String {
            return when(id) {
                SENSOR_O2_ID -> "Sat. Oxígeno"
                SENSOR_HEART_ID -> "Frec. Cardiaca"
                SENSOR_BLOOD_ID -> "Temperatura"
                else -> EMPTY_STRING
            }
        }

        fun getSensorSufixByID(id: Int): String {
            return when(id) {
                SENSOR_O2_ID -> " %"
                SENSOR_HEART_ID -> " Lpm"
                SENSOR_BLOOD_ID -> " °C"
                else -> EMPTY_STRING
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
    }
}