package com.mapx.kosten.mosimpa.data.entities

import com.google.gson.annotations.SerializedName

data class SensorBloopP(
    @SerializedName("time") val time : Long,
    @SerializedName("sys") val sys : Int,
    @SerializedName("dia") val dia : Int
)