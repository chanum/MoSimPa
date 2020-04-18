package com.mapx.kosten.mosimpa.presentation.fragments.patients

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.mapx.kosten.mosimpa.domain.entites.PatientEntity
import com.mapx.kosten.mosimpa.domain.interactors.device.ObserveDevicesUseCase
import com.mapx.kosten.mosimpa.domain.interactors.device.SubscribeToAllDevices
import com.mapx.kosten.mosimpa.domain.interactors.patient.GetPatientsUseCase
import com.mapx.kosten.mosimpa.domain.interactors.patient.ObservePatientsUseCase
import com.mapx.kosten.mosimpa.domain.interactors.patient.SavePatientUseCase
import com.mapx.kosten.mosimpa.domain.interactors.sensor.ConnectClientMqttUseCase
import com.mapx.kosten.mosimpa.presentation.common.SingleLiveEvent
import kotlinx.coroutines.*

class PatientsViewModel(
    private val getPatientsUseCase: GetPatientsUseCase,
    private val connectClientMqttUseCase: ConnectClientMqttUseCase,
    private val subscribeToAllDevices: SubscribeToAllDevices,
    private val observePatientsUseCase: ObservePatientsUseCase,
    private val observeDevicesUseCase: ObserveDevicesUseCase,
    private val savePatientUseCase: SavePatientUseCase
): ViewModel() {

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    // TODO see FLow
    var patients: LiveData<List<PatientEntity>> = observePatientsUseCase.invoke()
    var devices: LiveData<String> = observeDevicesUseCase.invoke()

    var viewState: MutableLiveData<PatientsViewState> = MutableLiveData()
    var errorState: SingleLiveEvent<Throwable?> = SingleLiveEvent()

    init {
        val viewState = PatientsViewState()
        this.viewState.value = viewState
    }

    fun connectAndSubscribeToAll() {
        viewModelScope.launch {
            connectClientMqttUseCase.invoke()
        }
    }

    fun updateDevices(device: String) {
        val patient = PatientEntity(deviceId = device)
        // TODO check if deviceID exits
        // save Patient
        viewModelScope.launch {
            savePatientUseCase.invoke(patient)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
