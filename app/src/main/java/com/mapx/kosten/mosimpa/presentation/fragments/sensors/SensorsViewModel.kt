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

    var currentInternment = InternmentEntity()
    var errorState: SingleLiveEvent<Throwable?> = SingleLiveEvent()
    // TODO see FLow
    var sensorO2Value: LiveData<SensorO2Entity> = getO2DataUseCase.invoke(currentInternment)
//    val sensorO2Value = liveData(Dispatchers.IO)  {
//        val value = getO2DataUseCase.invoke(currentPatient)
//        emit(value)
//    }
    var sensorBloodValue: LiveData<SensorBloodEntity> = getBloodDataUseCase.invoke(currentInternment)
    var sensorHeartValue: LiveData<SensorHeartEntity> = getHeartDataUseCase.invoke(currentInternment)
    var sensorTempValue: LiveData<SensorTempEntity> = getTempDataUseCase.invoke(currentInternment)

    fun subscribePatient(id: Long) {
        var deviceId = ""
        viewModelScope.launch {
            deviceId = getDeviceIdByInternmentId.invoke(id)
            val internment = InternmentEntity(deviceId = deviceId, id = id)
            subscribeIdUseCase.invoke(internment)
            currentInternment = internment
        }
    }

    override fun onCleared() {
        super.onCleared()
        currentInternment = InternmentEntity()
        // subscribeIdUseCase.invoke(currentInternment)
    }

}