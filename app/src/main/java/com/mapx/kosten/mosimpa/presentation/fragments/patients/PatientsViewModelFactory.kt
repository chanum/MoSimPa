package com.mapx.kosten.mosimpa.presentation.fragments.patients

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mapx.kosten.mosimpa.domain.interactors.patient.GetPatientsUseCase
import com.mapx.kosten.mosimpa.domain.interactors.sensor.ConnectClientMqttUseCase

class PatientsViewModelFactory (
    private val getPatientsUseCase: GetPatientsUseCase,
    private val connectClientMqttUseCase: ConnectClientMqttUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PatientsViewModel(
            getPatientsUseCase,
            connectClientMqttUseCase
        ) as T
    }
}