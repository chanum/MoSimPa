package com.mapx.kosten.mosimpa.data.entities

import com.google.gson.annotations.SerializedName

const val SENSOR_BLOOD_JSON_KEY = "bloodP"

data class SensorBloopRsp(
    @SerializedName(SENSOR_BLOOD_JSON_KEY) val bloodP : List<SensorBloop>
)