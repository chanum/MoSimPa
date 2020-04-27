package com.mapx.kosten.mosimpa.domain.entites

data class SensorBloodEntity (
    var id: Int = -1,
    val patientId: Long = -1,
    val time: Long = -1,
    val sys: Int = 0,
    val dia: Int = 0
)