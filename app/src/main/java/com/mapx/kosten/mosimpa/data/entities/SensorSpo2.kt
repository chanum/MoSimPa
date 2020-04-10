package com.mapx.kosten.mosimpa.data.entities

import com.google.gson.annotations.SerializedName

data class SensorSpo2(
    @SerializedName("time") val time : Long,
    @SerializedName("SpO2") val spO2 : Float,
    @SerializedName("R") val r : Float
)