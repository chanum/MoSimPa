package com.mapx.kosten.mosimpa.presentation.fragments.sensors

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.mapx.kosten.mosimpa.domain.common.Constants
import com.mapx.kosten.mosimpa.domain.common.Constants.Companion.SENSOR_HR_ID
import com.mapx.kosten.mosimpa.domain.common.Constants.Companion.SENSOR_SPO2_ID
import com.mapx.kosten.mosimpa.domain.common.Constants.Companion.SENSOR_TEMP_ID
import com.mapx.kosten.mosimpa.domain.entites.SensorEntity
import com.mapx.kosten.mosimpa.domain.interactors.sensor.GetSensorDataUseCase
import com.mapx.kosten.mosimpa.presentation.common.BaseViewModel
import com.mapx.kosten.mosimpa.presentation.common.SingleLiveEvent
import com.mapx.kosten.mosimpa.presentation.common.Utils.Companion.getSensorNameById

class SensorsViewModel(
    private val getSensorDataUseCase: GetSensorDataUseCase
): BaseViewModel() {

    var spo2State: MutableLiveData<SensorEntity> = MutableLiveData()
    var hrState: MutableLiveData<SensorEntity> = MutableLiveData()
    var tempState: MutableLiveData<SensorEntity> = MutableLiveData()
    var errorState: SingleLiveEvent<Throwable?> = SingleLiveEvent()

    init {

    }

    fun getSensorData() {
        addDisposable(getSensorDataUseCase.observable()
            .subscribe({ nodes ->
                errorState.value = null
                Log.i(javaClass.simpleName, "Rcv Ok")
            } , {
                errorState.value = it
                Log.i(javaClass.simpleName, "Rcv Error")
            })
        )
    }
}