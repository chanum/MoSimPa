package com.mapx.kosten.mosimpa.data.entities

import com.google.gson.annotations.SerializedName

const val SENSOR_TEMP_JSON_KEY = "bodyT"

data class SensorBodyTRsp(
    @SerializedName(SENSOR_TEMP_JSON_KEY) val bodyT : List<SensorBodyT>
)