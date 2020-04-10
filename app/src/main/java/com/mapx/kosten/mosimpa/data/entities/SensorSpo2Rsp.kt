package com.mapx.kosten.mosimpa.data.entities

import com.google.gson.annotations.SerializedName

data class SensorSpo2Rsp(
    @SerializedName("spo2") val spo2 : List<SensorSpo2>
)