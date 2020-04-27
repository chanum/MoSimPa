package com.mapx.kosten.mosimpa.domain.common

import com.mapx.kosten.mosimpa.domain.common.Constants.Companion.SENSOR_BLOOD_ID
import com.mapx.kosten.mosimpa.domain.common.Constants.Companion.SENSOR_HEART_ID
import com.mapx.kosten.mosimpa.domain.common.Constants.Companion.SENSOR_O2_ID
import com.mapx.kosten.mosimpa.domain.common.Constants.Companion.SENSOR_TEMPERATURE_ID

class Utils {
    companion object {
        fun convertLongToHexString(from: Long): String {
            return String.format("%02X", from)
        }

        fun scaleSensorValueByID(id: Int, value: Float): Float {
            return when(id) {
                SENSOR_O2_ID -> value / 10
                SENSOR_HEART_ID -> value / 10
                SENSOR_BLOOD_ID -> value
                SENSOR_TEMPERATURE_ID -> (value * 0.00390625).toFloat()
                else -> 0F
            }
        }
    }

}