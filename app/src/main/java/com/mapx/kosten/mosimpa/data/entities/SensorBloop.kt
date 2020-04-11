package com.mapx.kosten.mosimpa.data.entities

import com.google.gson.annotations.SerializedName

const val SENSOR_BLOOD_TIME_JSON_KEY = "time"
const val SENSOR_BLOOD_SYS_JSON_KEY = "sys"
const val SENSOR_BLOOD_DIA_JSON_KEY = "dia"

data class SensorBloop(
    @SerializedName(SENSOR_BLOOD_TIME_JSON_KEY) val time : Long,
    @SerializedName(SENSOR_BLOOD_SYS_JSON_KEY) val sys : Int,
    @SerializedName(SENSOR_BLOOD_DIA_JSON_KEY) val dia : Int
)