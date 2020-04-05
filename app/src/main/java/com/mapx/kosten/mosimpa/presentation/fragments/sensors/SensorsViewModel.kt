package com.mapx.kosten.mosimpa.presentation.fragments.sensors

import androidx.lifecycle.MutableLiveData
import com.mapx.kosten.mosimpa.presentation.common.BaseViewModel
import com.mapx.kosten.mosimpa.presentation.common.SingleLiveEvent

class SensorsViewModel(

): BaseViewModel() {

    var viewState: MutableLiveData<SensorsViewState> = MutableLiveData()
    var errorState: SingleLiveEvent<Throwable?> = SingleLiveEvent()

    init {
        val viewState = SensorsViewState()
        this.viewState.value = viewState
    }

    fun loadSensors() {

    }
}