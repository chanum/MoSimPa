package com.mapx.kosten.mosimpa.domain.entites

data class SensorO2Entity (
    var id: Int = -1,
    var patientId: Long = -1,
    var time: Long = -1,
    var spo2: Float = 0F,
    var r: Float = 0F
)