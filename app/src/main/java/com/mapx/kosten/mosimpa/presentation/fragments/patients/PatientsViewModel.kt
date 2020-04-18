package com.mapx.kosten.mosimpa.presentation.fragments.patients

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mapx.kosten.mosimpa.domain.entites.PatientEntity
import com.mapx.kosten.mosimpa.domain.interactors.device.SubscribeToAllDevices
import com.mapx.kosten.mosimpa.domain.interactors.patient.GetPatientsUseCase
import com.mapx.kosten.mosimpa.domain.interactors.patient.ObservePatientsUseCase
import com.mapx.kosten.mosimpa.domain.interactors.sensor.ConnectClientMqttUseCase
import com.mapx.kosten.mosimpa.presentation.common.SingleLiveEvent
import kotlinx.coroutines.*

class PatientsViewModel(
    private val getPatientsUseCase: GetPatientsUseCase,
    private val connectClientMqttUseCase: ConnectClientMqttUseCase,
    private val subscribeToAllDevices: SubscribeToAllDevices,
    private val observePatientsUseCase: ObservePatientsUseCase
): ViewModel() {

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    // TODO see FLow
    var patients: LiveData<List<PatientEntity>> = observePatientsUseCase.invoke()

    var viewState: MutableLiveData<PatientsViewState> = MutableLiveData()
    var errorState: SingleLiveEvent<Throwable?> = SingleLiveEvent()

    init {
        val viewState = PatientsViewState()
        this.viewState.value = viewState
    }

    fun scanDevices() {
        viewModelScope.launch {
            subscribeToAllDevices.invoke()
        }
    }

    fun connectAndSubscribeToAll() {
        viewModelScope.launch {
            connectClientMqttUseCase.invoke()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
