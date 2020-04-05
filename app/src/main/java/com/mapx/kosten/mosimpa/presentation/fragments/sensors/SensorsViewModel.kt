package com.mapx.kosten.mosimpa.presentation.fragments.sensors

import androidx.lifecycle.MutableLiveData
import com.mapx.kosten.mosimpa.domain.common.Constants
import com.mapx.kosten.mosimpa.domain.common.Constants.Companion.SENSOR_HR_ID
import com.mapx.kosten.mosimpa.domain.common.Constants.Companion.SENSOR_SPO2_ID
import com.mapx.kosten.mosimpa.domain.common.Constants.Companion.SENSOR_TEMP_ID
import com.mapx.kosten.mosimpa.domain.entites.SensorEntity
import com.mapx.kosten.mosimpa.presentation.common.BaseViewModel
import com.mapx.kosten.mosimpa.presentation.common.SingleLiveEvent
import com.mapx.kosten.mosimpa.presentation.common.Utils.Companion.getSensorNameById

class SensorsViewModel(

): BaseViewModel() {

    var viewState: MutableLiveData<SensorsViewState> = MutableLiveData()
    var errorState: SingleLiveEvent<Throwable?> = SingleLiveEvent()

    init {
        val viewState = SensorsViewState()
        this.viewState.value = viewState
    }

    fun loadSensors() {
        val sensors = mutableListOf<SensorEntity>()

        val spo2Sensor = SensorEntity (
            id = SENSOR_SPO2_ID,
            name = getSensorNameById(SENSOR_SPO2_ID),
            value = 0F
        )
        sensors.add(spo2Sensor)

        val hrSensor = SensorEntity (
            id = SENSOR_HR_ID,
            name = getSensorNameById(SENSOR_HR_ID),
            value = 0F
        )
        sensors.add(hrSensor)

        val tempSensor = SensorEntity (
            id = SENSOR_TEMP_ID,
            name = getSensorNameById(SENSOR_TEMP_ID),
            value = 0F
        )
        sensors.add(tempSensor)

        viewState.value = viewState.value?.copy(
            isLoading = false,
            isEmpty = false,
            sensors = sensors)
    }
}