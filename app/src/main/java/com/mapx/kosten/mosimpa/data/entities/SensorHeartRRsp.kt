package com.mapx.kosten.mosimpa.data.entities

import com.google.gson.annotations.SerializedName

data class SensorHeartRRsp(
    @SerializedName("heartR") val heartR : List<SensorHeartR>
)