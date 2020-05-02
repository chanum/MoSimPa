package com.mapx.kosten.mosimpa.domain.entites

data class SensorHeartEntity (
    var id: Int = -1,
    var internmentId: Long = -1,
    var time: Long = -1,
    var heartR: Int = 0,
    var HR_AR: Boolean = false
)