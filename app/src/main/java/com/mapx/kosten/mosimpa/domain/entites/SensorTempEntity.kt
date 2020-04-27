package com.mapx.kosten.mosimpa.domain.entites

data class SensorTempEntity (
    var id: Int = -1,
    var patientId: Long = -1,
    var time: Long = -1,
    var temp: Float = 0F
)