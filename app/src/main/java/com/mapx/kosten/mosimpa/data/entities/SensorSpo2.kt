package com.mapx.kosten.mosimpa.data.entities

import com.google.gson.annotations.SerializedName

const val SENSOR_O2_TIME_JSON_KEY = "time"
const val SENSOR_O2_SPO2_JSON_KEY = "SpO2"
const val SENSOR_O2_R_JSON_KEY = "R"

data class SensorSpo2(
    @SerializedName(SENSOR_O2_TIME_JSON_KEY) val time : Long,
    @SerializedName(SENSOR_O2_SPO2_JSON_KEY) val spO2 : Float,
    @SerializedName(SENSOR_O2_R_JSON_KEY) val r : Float
)