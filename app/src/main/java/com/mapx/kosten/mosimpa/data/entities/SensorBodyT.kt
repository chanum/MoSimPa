package com.mapx.kosten.mosimpa.data.entities

import com.google.gson.annotations.SerializedName

const val SENSOR_TEMP_TIME_JSON_KEY = "time"
const val SENSOR_TEMP_TEMP_JSON_KEY = "temp"

data class SensorBodyT(
    @SerializedName(SENSOR_TEMP_TIME_JSON_KEY) val time : Long,
    @SerializedName(SENSOR_TEMP_TEMP_JSON_KEY) val temp : Float
)