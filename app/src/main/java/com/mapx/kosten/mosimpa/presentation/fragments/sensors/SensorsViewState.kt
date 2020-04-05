package com.mapx.kosten.mosimpa.presentation.fragments.sensors

import com.mapx.kosten.mosimpa.domain.entites.SensorEntity

data class SensorsViewState(
    val isLoading: Boolean = true,
    val isEmpty: Boolean = true,
    val sensors: List<SensorEntity>? = null
)