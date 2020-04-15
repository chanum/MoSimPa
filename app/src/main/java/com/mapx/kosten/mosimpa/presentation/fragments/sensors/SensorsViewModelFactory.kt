package com.mapx.kosten.mosimpa.presentation.fragments.sensors

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mapx.kosten.mosimpa.domain.interactors.patient.GetDeviceIdByPatientId
import com.mapx.kosten.mosimpa.domain.interactors.sensor.*

class SensorsViewModelFactory(
    private val subscribeIdUseCase: SubscribeIdUseCase,
    private val unSubscribeIdUseCase: UnSubscribeIdUseCase,
    private val getO2DataUseCase: GetSensorO2DataUseCase,
    private val getBloodDataUseCase: GetSensorBloodDataUseCase,
    private val getHeartDataUseCase: GetSensorHeartDataUseCase,
    private val getTempDataUseCase: GetSensorTempDataUseCase,
    private val getDeviceIdByPatientId: GetDeviceIdByPatientId
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SensorsViewModel(
            subscribeIdUseCase,
            unSubscribeIdUseCase,
            getO2DataUseCase,
            getBloodDataUseCase,
            getHeartDataUseCase,
            getTempDataUseCase,
            getDeviceIdByPatientId
        ) as T
    }
}
