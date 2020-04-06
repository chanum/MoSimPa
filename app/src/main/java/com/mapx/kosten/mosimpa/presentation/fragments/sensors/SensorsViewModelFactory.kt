package com.mapx.kosten.mosimpa.presentation.fragments.sensors

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mapx.kosten.mosimpa.domain.interactors.sensor.GetSensorDataUseCase

class SensorsViewModelFactory(
    private val getSensorDataUseCase: GetSensorDataUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SensorsViewModel(getSensorDataUseCase) as T
    }
}
