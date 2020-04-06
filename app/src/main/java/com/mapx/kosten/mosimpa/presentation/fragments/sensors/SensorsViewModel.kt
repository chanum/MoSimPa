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

    var spo2State: MutableLiveData<SensorEntity> = MutableLiveData()
    var hrState: MutableLiveData<SensorEntity> = MutableLiveData()
    var tempState: MutableLiveData<SensorEntity> = MutableLiveData()
    var errorState: SingleLiveEvent<Throwable?> = SingleLiveEvent()

    init {

    }
}