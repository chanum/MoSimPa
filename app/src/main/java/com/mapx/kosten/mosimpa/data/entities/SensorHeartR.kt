package com.mapx.kosten.mosimpa.data.entities

import com.google.gson.annotations.SerializedName

const val SENSOR_HEART_TIME_JSON_KEY = "time"
const val SENSOR_HEART_HEARTR_JSON_KEY = "heartR"
const val SENSOR_HEART_HR_AR_JSON_KEY = "HR_AR"

data class SensorHeartR(
    @SerializedName(SENSOR_HEART_TIME_JSON_KEY) val time : Long,
    @SerializedName(SENSOR_HEART_HEARTR_JSON_KEY) val heartR : Int,
    @SerializedName(SENSOR_HEART_HR_AR_JSON_KEY) val HR_AR : Boolean
)