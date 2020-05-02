package com.mapx.kosten.mosimpa.presentation.entities

data class SensorView (
    var id: Int = -1,
    val name: String = "",
    var value: Float = -1F,
    var alarmMin: Float = 0F,
    var alarmMax: Float = 0F
)