package com.mapx.kosten.mosimpa.presentation.fragments.patients

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mapx.kosten.mosimpa.domain.interactors.device.ObserveDevicesUseCase
import com.mapx.kosten.mosimpa.domain.interactors.device.SubscribeToAllDevices
import com.mapx.kosten.mosimpa.domain.interactors.patient.GetPatientsUseCase
import com.mapx.kosten.mosimpa.domain.interactors.patient.ObservePatientsUseCase
import com.mapx.kosten.mosimpa.domain.interactors.patient.SavePatientUseCase
import com.mapx.kosten.mosimpa.domain.interactors.sensor.ConnectClientMqttUseCase

class PatientsViewModelFactory (
    private val getPatientsUseCase: GetPatientsUseCase,
    private val connectClientMqttUseCase: ConnectClientMqttUseCase,
    private val subscribeToAllDevices: SubscribeToAllDevices,
    private val observePatientsUseCase: ObservePatientsUseCase,
    private val observeDevicesUseCase: ObserveDevicesUseCase,
    private val savePatientUseCase: SavePatientUseCase
    ) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PatientsViewModel(
            getPatientsUseCase,
            connectClientMqttUseCase,
            subscribeToAllDevices,
            observePatientsUseCase,
            observeDevicesUseCase,
            savePatientUseCase
        ) as T
    }
}