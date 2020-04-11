package com.mapx.kosten.mosimpa.presentation.fragments.sensors

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mapx.kosten.mosimpa.domain.entites.*
import com.mapx.kosten.mosimpa.domain.interactors.sensor.*
import com.mapx.kosten.mosimpa.presentation.common.BaseViewModel
import com.mapx.kosten.mosimpa.presentation.common.SingleLiveEvent

class SensorsViewModel(
    private val getSensorDataUseCase: GetSensorDataUseCase,
    private val subscribeIdUseCase: SubscribeIdUseCase,
    private val getO2DataUseCase: GetSensorO2DataUseCase,
    private val getBloodDataUseCase: GetSensorBloodDataUseCase,
    private val getHeartDataUseCase: GetSensorHeartDataUseCase,
    private val getTempDataUseCase: GetSensorTempDataUseCase
): BaseViewModel() {


    var errorState: SingleLiveEvent<Throwable?> = SingleLiveEvent()
    var sensorO2Value: LiveData<SensorO2Entity> = getO2DataUseCase.invoke()
    var sensorBloodValue: LiveData<SensorBloodEntity> = getBloodDataUseCase.invoke()
    var sensorHeartValue: LiveData<SensorHeartEntity> = getHeartDataUseCase.invoke()
    var sensorTempValue: LiveData<SensorTempEntity> = getTempDataUseCase.invoke()

    private fun loadSpo2Sensor() {
//        GlobalScope.launch {
//            spo2List.postValue(getSpo2DataUseCase.invoke())
//        }
    }


    fun subscribePatient(Id: Long) {
        // TODO get true id
        val hardId = 0xb827eb8b862d
        addDisposable(subscribeIdUseCase.subscribe(hardId)
            .subscribe({
                errorState.value = null
                Log.i(javaClass.simpleName, "subscribePatient Ok")
                // getSensorData(hardId)
                loadSpo2Sensor()
            } , {
                errorState.value = it
                Log.i(javaClass.simpleName, "Error subscribePatient")
            })
        )
    }

    private fun getSensorData(Id: Long) {
        addDisposable(getSensorDataUseCase.getDataById(Id)
            .subscribe({
                errorState.value = null
                Log.i(javaClass.simpleName, "getSensorData Ok $it")
            } , {
                errorState.value = it
                Log.i(javaClass.simpleName, "Error getSensorData")
            })
        )
    }
}