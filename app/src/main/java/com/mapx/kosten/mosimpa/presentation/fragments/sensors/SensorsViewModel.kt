package com.mapx.kosten.mosimpa.presentation.fragments.sensors

import androidx.lifecycle.*
import com.mapx.kosten.mosimpa.domain.entites.*
import com.mapx.kosten.mosimpa.domain.interactors.internments.GetDeviceIdByInternmentId
import com.mapx.kosten.mosimpa.domain.interactors.sensor.*
import com.mapx.kosten.mosimpa.presentation.common.SingleLiveEvent
import kotlinx.coroutines.*

class SensorsViewModel(
    private val subscribeIdUseCase: SubscribeIdUseCase,
    private val unSubscribeIdUseCase: UnSubscribeIdUseCase,
    private val getO2DataUseCase: GetSensorO2DataUseCase,
    private val getBloodDataUseCase: GetSensorBloodDataUseCase,
    private val getHeartDataUseCase: GetSensorHeartDataUseCase,
    private val getTempDataUseCase: GetSensorTempDataUseCase,
    private val getDeviceIdByInternmentId: GetDeviceIdByInternmentId

): ViewModel() {

    var errorState: SingleLiveEvent<Throwable?> = SingleLiveEvent()
    // TODO see FLow
    var sensorO2Value: LiveData<SensorO2Entity> = getO2DataUseCase.invoke()
//    val sensorO2Value = liveData(Dispatchers.IO)  {
//        val value = getO2DataUseCase.invoke(currentPatient)
//        emit(value)
//    }
    var sensorBloodValue: LiveData<SensorBloodEntity> = getBloodDataUseCase.invoke()
    var sensorHeartValue: LiveData<SensorHeartEntity> = getHeartDataUseCase.invoke()
    var sensorTempValue: LiveData<SensorTempEntity> = getTempDataUseCase.invoke()

}