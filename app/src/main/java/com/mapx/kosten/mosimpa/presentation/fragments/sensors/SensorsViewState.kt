package com.mapx.kosten.mosimpa.presentation.fragments.sensors

import com.mapx.kosten.mosimpa.domain.entites.SensorEntity

data class SensorsViewState(
    val isLoading: Boolean = true,
    val isEmpty: Boolean = true,
    val sensorSpo2: Float = 0F,
    val sensorHr: Float = 0F,
    val sensorTemp: Float = 0F
)