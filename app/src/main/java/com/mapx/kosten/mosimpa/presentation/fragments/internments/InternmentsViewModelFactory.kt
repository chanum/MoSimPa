package com.mapx.kosten.mosimpa.presentation.fragments.internments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mapx.kosten.mosimpa.domain.interactors.device.ObserveDevicesUseCase
import com.mapx.kosten.mosimpa.domain.interactors.device.SubscribeToAllDevices
import com.mapx.kosten.mosimpa.domain.interactors.patient.GetInternmentsUseCase
import com.mapx.kosten.mosimpa.domain.interactors.patient.GetPatientsUseCase
import com.mapx.kosten.mosimpa.domain.interactors.patient.ObservePatientsUseCase
import com.mapx.kosten.mosimpa.domain.interactors.patient.SavePatientUseCase
import com.mapx.kosten.mosimpa.domain.interactors.sensor.ConnectClientMqttUseCase

class InternmentsViewModelFactory (
    private val connectClientMqttUseCase: ConnectClientMqttUseCase,
    private val getInternmentsUseCase: GetInternmentsUseCase
    ) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return InternmentsViewModel(
            connectClientMqttUseCase,
            getInternmentsUseCase
        ) as T
    }
}