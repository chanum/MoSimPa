package com.mapx.kosten.mosimpa.presentation.fragments.sensors

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mapx.kosten.mosimpa.domain.interactors.sensor.*

class SensorsViewModelFactory(
    private val getSensorDataUseCase: GetSensorDataUseCase,
    private val subscribeIdUseCase: SubscribeIdUseCase,
    private val getO2DataUseCase: GetSensorO2DataUseCase,
    private val getBloodDataUseCase: GetSensorBloodDataUseCase,
    private val getHeartDataUseCase: GetSensorHeartDataUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SensorsViewModel(
            getSensorDataUseCase,
            subscribeIdUseCase,
            getO2DataUseCase,
            getBloodDataUseCase,
            getHeartDataUseCase
        ) as T
    }
}
