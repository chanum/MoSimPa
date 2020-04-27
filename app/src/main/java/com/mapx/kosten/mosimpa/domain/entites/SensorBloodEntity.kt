package com.mapx.kosten.mosimpa.domain.entites

data class SensorBloodEntity (
    var id: Int = -1,
    var patientId: Long = -1,
    var time: Long = -1,
    var sys: Int = 0,
    var dia: Int = 0
)