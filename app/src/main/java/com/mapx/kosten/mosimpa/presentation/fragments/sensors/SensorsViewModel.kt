package com.mapx.kosten.mosimpa.presentation.fragments.sensors

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mapx.kosten.mosimpa.domain.entites.*
import com.mapx.kosten.mosimpa.domain.interactors.sensor.*
import com.mapx.kosten.mosimpa.presentation.common.BaseViewModel
import com.mapx.kosten.mosimpa.presentation.common.SingleLiveEvent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SensorsViewModel(
    private val subscribeIdUseCase: SubscribeIdUseCase,
    private val getO2DataUseCase: GetSensorO2DataUseCase,
    private val getBloodDataUseCase: GetSensorBloodDataUseCase,
    private val getHeartDataUseCase: GetSensorHeartDataUseCase,
    private val getTempDataUseCase: GetSensorTempDataUseCase
): BaseViewModel() {

    var errorState: SingleLiveEvent<Throwable?> = SingleLiveEvent()
    var sensorO2Value: LiveData<SensorO2Entity> = getO2DataUseCase.invoke(-1)
    var sensorBloodValue: LiveData<SensorBloodEntity> = getBloodDataUseCase.invoke(-1)
    var sensorHeartValue: LiveData<SensorHeartEntity> = getHeartDataUseCase.invoke(-1)
    var sensorTempValue: LiveData<SensorTempEntity> = getTempDataUseCase.invoke(-1)

    fun subscribePatient(id: Long) {
        // TODO get true id
        // val hardId = 0xb827eb8b862d
        addDisposable(subscribeIdUseCase.subscribe(id)
            .subscribe({
                errorState.value = null
                Log.i(javaClass.simpleName, "subscribePatient Ok")
                // waitForSensorsData(id)
            } , {
                errorState.value = it
                Log.i(javaClass.simpleName, "Error subscribePatient")
            })
        )
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