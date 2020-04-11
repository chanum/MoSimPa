package com.mapx.kosten.mosimpa.data.entities

import com.google.gson.annotations.SerializedName

const val SENSOR_O2_JSON_KEY = "spo2"

data class SensorSpo2Rsp(
    @SerializedName(SENSOR_O2_JSON_KEY) val spo2 : List<SensorSpo2>
)