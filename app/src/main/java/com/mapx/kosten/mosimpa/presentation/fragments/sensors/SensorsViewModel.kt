package com.mapx.kosten.mosimpa.presentation.fragments.sensors

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.mapx.kosten.mosimpa.domain.entites.SensorEntity
import com.mapx.kosten.mosimpa.domain.entites.SensorSpo2Entity
import com.mapx.kosten.mosimpa.domain.interactors.sensor.GetSensorDataUseCase
import com.mapx.kosten.mosimpa.domain.interactors.sensor.GetSensorSpo2DataUseCase
import com.mapx.kosten.mosimpa.domain.interactors.sensor.SubscribeIdUseCase
import com.mapx.kosten.mosimpa.presentation.common.BaseViewModel
import com.mapx.kosten.mosimpa.presentation.common.SingleLiveEvent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SensorsViewModel(
    private val getSensorDataUseCase: GetSensorDataUseCase,
    private val subscribeIdUseCase: SubscribeIdUseCase,
    private val getSpo2DataUseCase: GetSensorSpo2DataUseCase
): BaseViewModel() {

    var spo2List: MutableLiveData<List<SensorEntity>> = MutableLiveData()
    var hrState: MutableLiveData<SensorEntity> = MutableLiveData()
    var tempState: MutableLiveData<SensorEntity> = MutableLiveData()
    var errorState: SingleLiveEvent<Throwable?> = SingleLiveEvent()

    var spo2Value: LiveData<SensorSpo2Entity> = getSpo2DataUseCase.invoke()

    init {

    }

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