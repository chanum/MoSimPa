package com.mapx.kosten.mosimpa.data.entities

import com.google.gson.annotations.SerializedName

const val SENSOR_HEART_JSON_KEY = "heartR"

data class SensorHeartRRsp(
    @SerializedName(SENSOR_HEART_JSON_KEY) val heartR : List<SensorHeartR>
)