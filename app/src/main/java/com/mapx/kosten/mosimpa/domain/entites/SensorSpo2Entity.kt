package com.mapx.kosten.mosimpa.domain.entites

data class SensorSpo2Entity (
    var id: Int = -1,
    val deviceId: Long = -1,
    val time: Long = -1,
    val spo2: Float = -1F,
    val r: Float = -1F
)