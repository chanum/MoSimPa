package com.mapx.kosten.mosimpa.domain.entites

data class SensorO2Entity (
    var id: Int = -1,
    val patientId: Long = -1,
    val time: Long = -1,
    val spo2: Float = -1F,
    val r: Float = -1F
)