package com.mapx.kosten.mosimpa.presentation.fragments.sensors

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mapx.kosten.mosimpa.domain.entites.*
import com.mapx.kosten.mosimpa.domain.interactors.patient.GetDeviceIdByPatientId
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
    private val getDeviceIdByPatientId: GetDeviceIdByPatientId
): ViewModel() {

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    var currentPatient = PatientEntity()
    var errorState: SingleLiveEvent<Throwable?> = SingleLiveEvent()
    // TODO see FLow
    var sensorO2Value: LiveData<SensorO2Entity> = getO2DataUseCase.invoke(currentPatient)
    var sensorBloodValue: LiveData<SensorBloodEntity> = getBloodDataUseCase.invoke(currentPatient)
    var sensorHeartValue: LiveData<SensorHeartEntity> = getHeartDataUseCase.invoke(currentPatient)
    var sensorTempValue: LiveData<SensorTempEntity> = getTempDataUseCase.invoke(currentPatient)


    fun subscribePatient(id: Long) {
        var deviceId = ""
        viewModelScope.launch {
            deviceId = getDeviceIdByPatientId.invoke(id)
            val patient = PatientEntity(deviceId = deviceId, id = id)
            subscribeIdUseCase.invoke(patient)
            currentPatient = patient
        }
    }

    override fun onCleared() {
        super.onCleared()
        currentPatient = PatientEntity()
        sensorO2Value = getO2DataUseCase.invoke(currentPatient)
        sensorBloodValue = getBloodDataUseCase.invoke(currentPatient)
        sensorHeartValue = getHeartDataUseCase.invoke(currentPatient)
        sensorTempValue = getTempDataUseCase.invoke(currentPatient)
        viewModelJob.cancel()
    }

}