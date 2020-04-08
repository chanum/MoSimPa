package com.mapx.kosten.mosimpa.presentation.fragments.sensors

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mapx.kosten.mosimpa.domain.interactors.sensor.GetSensorDataUseCase
import com.mapx.kosten.mosimpa.domain.interactors.sensor.GetSensorSpo2DataUseCase
import com.mapx.kosten.mosimpa.domain.interactors.sensor.SubscribeIdUseCase

class SensorsViewModelFactory(
    private val getSensorDataUseCase: GetSensorDataUseCase,
    private val subscribeIdUseCase: SubscribeIdUseCase,
    private val getSpo2DataUseCase: GetSensorSpo2DataUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SensorsViewModel(
            getSensorDataUseCase,
            subscribeIdUseCase,
            getSpo2DataUseCase
        ) as T
    }
}
