package com.mapx.kosten.mosimpa.domain.entites

data class SensorHeartEntity (
    var id: Int = -1,
    val patientId: Long = -1,
    val time: Long = -1,
    val heartR: Int = -1,
    val HR_AR: Boolean = false
)