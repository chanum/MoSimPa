package com.mapx.kosten.mosimpa.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mapx.kosten.mosimpa.domain.interactors.internments.GetInternmentsUseCase
import com.mapx.kosten.mosimpa.domain.interactors.sensor.*

class InternmentsViewModelFactory (
    private val connectClientMqttUseCase: ConnectClientMqttUseCase,
    private val getInternmentsUseCase: GetInternmentsUseCase,
    private val updateInternmentsUseCase: UpdateInternmentsUseCase,
    private val subscribeIdUseCase: SubscribeIdUseCase,
    private val getO2DataUseCase: GetSensorO2DataUseCase,
    private val getBloodDataUseCase: GetSensorBloodDataUseCase,
    private val getHeartDataUseCase: GetSensorHeartDataUseCase,
    private val getTempDataUseCase: GetSensorTempDataUseCase
    ) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return InternmentsViewModel(
            connectClientMqttUseCase,
            getInternmentsUseCase,
            updateInternmentsUseCase,
            subscribeIdUseCase,
            getO2DataUseCase,
            getBloodDataUseCase,
            getHeartDataUseCase,
            getTempDataUseCase
        ) as T
    }
}