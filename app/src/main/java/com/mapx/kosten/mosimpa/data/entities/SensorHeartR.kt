package com.mapx.kosten.mosimpa.data.entities

import com.google.gson.annotations.SerializedName

data class SensorHeartR(
    @SerializedName("time") val time : Long,
    @SerializedName("heartR") val heartR : Int,
    @SerializedName("HR_AR") val HR_AR : Boolean
)