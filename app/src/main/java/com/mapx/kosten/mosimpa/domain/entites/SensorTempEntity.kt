package com.mapx.kosten.mosimpa.domain.entites

data class SensorTempEntity (
    var id: Int = -1,
    val patientId: Long = -1,
    val time: Long = -1,
    val temp: Float = 0F
)