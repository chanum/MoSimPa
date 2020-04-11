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
    private val unSubscribeIdUseCase: UnSubscribeIdUseCase,
    private val getO2DataUseCase: GetSensorO2DataUseCase,
    private val getBloodDataUseCase: GetSensorBloodDataUseCase,
    private val getHeartDataUseCase: GetSensorHeartDataUseCase,
    private val getTempDataUseCase: GetSensorTempDataUseCase
): BaseViewModel() {

    var currentId = INVALID_ID
    // var currentId: LiveData<Long> = MutableLiveData<Long>()
    var errorState: SingleLiveEvent<Throwable?> = SingleLiveEvent()
    var sensorO2Value: LiveData<SensorO2Entity> = getO2DataUseCase.invoke(currentId)
    var sensorBloodValue: LiveData<SensorBloodEntity> = getBloodDataUseCase.invoke(currentId)
    var sensorHeartValue: LiveData<SensorHeartEntity> = getHeartDataUseCase.invoke(currentId)
    var sensorTempValue: LiveData<SensorTempEntity> = getTempDataUseCase.invoke(currentId)


    fun subscribePatient(id: Long) {
        subscribeIdUseCase.invoke(id)
        currentId = id
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
        currentId = INVALID_ID
        sensorO2Value = getO2DataUseCase.invoke(currentId)
        sensorBloodValue = getBloodDataUseCase.invoke(currentId)
        sensorHeartValue = getHeartDataUseCase.invoke(currentId)
        sensorTempValue = getTempDataUseCase.invoke(currentId)
    }

    /*
    private fun waitForSensorsData(id: Long) {
        sensorO2Value = getO2DataUseCase.invoke(id)
        sensorBloodValue = getBloodDataUseCase.invoke(id)
        sensorHeartValue = getHeartDataUseCase.invoke(id)
        sensorTempValue = getTempDataUseCase.invoke(id)
    }
    */
    companion object{
        const val INVALID_ID = -1L
    }
}