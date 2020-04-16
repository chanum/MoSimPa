package com.mapx.kosten.mosimpa.presentation.fragments.sensors

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.mapx.kosten.mosimpa.domain.entites.*
import com.mapx.kosten.mosimpa.domain.interactors.patient.GetDeviceIdByPatientId
import com.mapx.kosten.mosimpa.domain.interactors.sensor.*
import com.mapx.kosten.mosimpa.presentation.common.BaseViewModel
import com.mapx.kosten.mosimpa.presentation.common.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SensorsViewModel(
    private val subscribeIdUseCase: SubscribeIdUseCase,
    private val unSubscribeIdUseCase: UnSubscribeIdUseCase,
    private val getO2DataUseCase: GetSensorO2DataUseCase,
    private val getBloodDataUseCase: GetSensorBloodDataUseCase,
    private val getHeartDataUseCase: GetSensorHeartDataUseCase,
    private val getTempDataUseCase: GetSensorTempDataUseCase,
    private val getDeviceIdByPatientId: GetDeviceIdByPatientId
): BaseViewModel() {

    var currentPatient = PatientEntity()
    // var currentId: LiveData<Long> = MutableLiveData<Long>()
    var errorState: SingleLiveEvent<Throwable?> = SingleLiveEvent()
    var sensorO2Value: LiveData<SensorO2Entity> = getO2DataUseCase.invoke(currentPatient)
    var sensorBloodValue: LiveData<SensorBloodEntity> = getBloodDataUseCase.invoke(currentPatient)
    var sensorHeartValue: LiveData<SensorHeartEntity> = getHeartDataUseCase.invoke(currentPatient)
    var sensorTempValue: LiveData<SensorTempEntity> = getTempDataUseCase.invoke(currentPatient)


    fun subscribePatient(id: Long) {
        // get deviceID
        // TODO
        // val deviceID = "b827eb8b862d"
        var deviceId = ""
        viewModelScope.launch {
            deviceId = getDeviceIdByPatientId.invoke(id)
            val patient = PatientEntity(deviceId = deviceId, id = id)
            // subscribeId(patient)
            subscribeIdUseCase.invoke(patient)
            currentPatient = patient
        }
    }

    private suspend fun getDeviceId(id: Long): String {
        var deviceId = ""
        withContext(Dispatchers.IO){
            deviceId = getDeviceIdByPatientId.invoke(id)
        }
        return deviceId
    }

    private suspend fun subscribeId(patient: PatientEntity) {
        withContext(Dispatchers.IO){
            subscribeIdUseCase.invoke(patient)
        }
    }

    override fun onCleared() {
        super.onCleared()
        currentPatient = PatientEntity()
        sensorO2Value = getO2DataUseCase.invoke(currentPatient)
        sensorBloodValue = getBloodDataUseCase.invoke(currentPatient)
        sensorHeartValue = getHeartDataUseCase.invoke(currentPatient)
        sensorTempValue = getTempDataUseCase.invoke(currentPatient)
    }

}