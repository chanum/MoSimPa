package com.mapx.kosten.mosimpa.presentation.fragments.patients

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mapx.kosten.mosimpa.domain.interactors.device.SubscribeToAllDevices
import com.mapx.kosten.mosimpa.domain.interactors.patient.GetPatientsUseCase
import com.mapx.kosten.mosimpa.domain.interactors.sensor.ConnectClientMqttUseCase
import com.mapx.kosten.mosimpa.presentation.common.SingleLiveEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class PatientsViewModel(
    private val getPatientsUseCase: GetPatientsUseCase,
    private val connectClientMqttUseCase: ConnectClientMqttUseCase,
    private val subscribeToAllDevices: SubscribeToAllDevices
): ViewModel() {

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

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

    fun connect() {
        viewModelScope.launch {
            connectClientMqttUseCase.invoke()
        }
    }

    fun loadPatients() {
        viewModelScope.launch {
            val patients = getPatientsUseCase.invoke()
            val newViewState = viewState.value?.copy(
                isEmpty = patients.isEmpty(),
                isLoading = false,
                patients = patients)
            viewState.value = newViewState
            errorState.value = null
            Log.i(javaClass.simpleName, "Rcv Ok")
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
