package com.mapx.kosten.mosimpa.data.entities

import com.google.gson.annotations.SerializedName

data class SensorBloopPRsp(
    @SerializedName("bloodP") val bloodP : List<SensorBloopP>
)