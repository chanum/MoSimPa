package com.mapx.kosten.mosimpa.presentation.fragments.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mapx.kosten.mosimpa.domain.interactors.patient.GetPatientsUseCase
import com.mapx.kosten.mosimpa.domain.interactors.settings.GetBrokerConfigUseCase
import com.mapx.kosten.mosimpa.domain.interactors.settings.SetBrokerConfigUseCase

class SettingsViewModelFactory (
    private val getPatientsUseCase: GetPatientsUseCase,
    private val getBrokerConfigUseCase: GetBrokerConfigUseCase,
    private val setBrokerConfigUseCase: SetBrokerConfigUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SettingsViewModel(
            getPatientsUseCase,
            getBrokerConfigUseCase,
            setBrokerConfigUseCase
        ) as T
    }
}