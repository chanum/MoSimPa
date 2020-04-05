package com.mapx.kosten.mosimpa.presentation.common

import com.mapx.kosten.mosimpa.domain.common.Constants.Companion.EMPTY_STRING
import com.mapx.kosten.mosimpa.domain.common.Constants.Companion.SENSOR_HR_ID
import com.mapx.kosten.mosimpa.domain.common.Constants.Companion.SENSOR_SPO2_ID
import com.mapx.kosten.mosimpa.domain.common.Constants.Companion.SENSOR_TEMP_ID

class Utils {

    companion object {
        fun getSensorNameById(id: Int): String {
            return when(id) {
                SENSOR_SPO2_ID -> "SPO2"
                SENSOR_HR_ID -> "Pulaciones"
                SENSOR_TEMP_ID -> "Temperatura"
                else -> EMPTY_STRING
            }
        }
    }
}