package com.mapx.kosten.mosimpa.presentation.fragments.sensors

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.mapx.kosten.mosimpa.domain.entites.*
import com.mapx.kosten.mosimpa.domain.interactors.sensor.*
import com.mapx.kosten.mosimpa.presentation.common.BaseViewModel
import com.mapx.kosten.mosimpa.presentation.common.SingleLiveEvent
import kotlinx.coroutines.launch

class SensorsViewModel(
    private val subscribeIdUseCase: SubscribeIdUseCase,
    private val unSubscribeIdUseCase: UnSubscribeIdUseCase,
    private val getO2DataUseCase: GetSensorO2DataUseCase,
    private val getBloodDataUseCase: GetSensorBloodDataUseCase,
    private val getHeartDataUseCase: GetSensorHeartDataUseCase,
    private val getTempDataUseCase: GetSensorTempDataUseCase
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
        // val id = 0xb827eb8b862d
        val deviceID = "b827eb8b862d"
        // create patient request
        val patient = PatientEntity(deviceId = deviceID, id = 1)
        viewModelScope.launch {
            subscribeIdUseCase.invoke(patient)
            currentPatient = patient
        }
        // TODO get true id
        // val hardId = 0xb827eb8b862d
        /*
        addDisposable(subscribeIdUseCase.subscribe(id)
            .subscribe({
                errorState.value = null
                Log.i(javaClass.simpleName, "subscribePatient Ok")
                currentId = id
                // waitForSensorsData(id)
            } , {
                errorState.value = it
                Log.i(javaClass.simpleName, "Error subscribePatient")
            })
        )
         */
    }

    override fun onCleared() {
        super.onCleared()
        // unSubscribeIdUseCase.invoke(currentId)
        currentPatient = PatientEntity()
        sensorO2Value = getO2DataUseCase.invoke(currentPatient)
        sensorBloodValue = getBloodDataUseCase.invoke(currentPatient)
        sensorHeartValue = getHeartDataUseCase.invoke(currentPatient)
        sensorTempValue = getTempDataUseCase.invoke(currentPatient)
    }

    /*
    private fun waitForSensorsData(id: Long) {
        sensorO2Value = getO2DataUseCase.invoke(id)
        sensorBloodValue = getBloodDataUseCase.invoke(id)
        sensorHeartValue = getHeartDataUseCase.invoke(id)
        sensorTempValue = getTempDataUseCase.invoke(id)
    }
    */
}