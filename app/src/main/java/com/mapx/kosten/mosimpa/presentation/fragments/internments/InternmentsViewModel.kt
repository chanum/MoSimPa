package com.mapx.kosten.mosimpa.presentation.fragments.internments

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mapx.kosten.mosimpa.domain.entites.InternmentEntity
import com.mapx.kosten.mosimpa.domain.interactors.patient.GetInternmentsUseCase
import com.mapx.kosten.mosimpa.domain.interactors.sensor.ConnectClientMqttUseCase
import kotlinx.coroutines.launch

class InternmentsViewModel(
    private val connectClientMqttUseCase: ConnectClientMqttUseCase,
    private val getInternmentsUseCase: GetInternmentsUseCase
): ViewModel() {

    // TODO see FLow
    var internments: LiveData<List<InternmentEntity>> = getInternmentsUseCase.invoke()

    fun connectAndSubscribeToAll() {
        viewModelScope.launch {
            connectClientMqttUseCase.invoke()
        }
    }

}
