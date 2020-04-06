package com.mapx.kosten.mosimpa.presentation.common

import com.mapx.kosten.mosimpa.domain.common.Constants.Companion.EMPTY_STRING
import com.mapx.kosten.mosimpa.domain.common.Constants.Companion.SENSOR_HR_ID
import com.mapx.kosten.mosimpa.domain.common.Constants.Companion.SENSOR_SPO2_ID
import com.mapx.kosten.mosimpa.domain.common.Constants.Companion.SENSOR_TEMP_ID

class Utils {

    companion object {
        const val INVALID_PATIENT_ID = -1L

        fun getSensorNameById(id: Int): String {
            return when(id) {
                SENSOR_SPO2_ID -> "Sat. Oxígeno"
                SENSOR_HR_ID -> "Frec. Cardiaca"
                SENSOR_TEMP_ID -> "Temperatura"
                else -> EMPTY_STRING
            }
        }

        fun getSensorSufixByID(id: Int): String {
            return when(id) {
                SENSOR_SPO2_ID -> " %"
                SENSOR_HR_ID -> " Lpm"
                SENSOR_TEMP_ID -> " °C"
                else -> EMPTY_STRING
            }
        }

        fun getSensorMaxValueByID(id: Int): Int {
            return when(id) {
                SENSOR_SPO2_ID -> 100
                SENSOR_HR_ID -> 200
                SENSOR_TEMP_ID -> 50
                else -> 0
            }
        }

        fun getSensorMinValueByID(id: Int): Int {
            return when(id) {
                SENSOR_SPO2_ID -> 40
                SENSOR_HR_ID -> 0
                SENSOR_TEMP_ID -> 0
                else -> 0
            }
        }
    }
}