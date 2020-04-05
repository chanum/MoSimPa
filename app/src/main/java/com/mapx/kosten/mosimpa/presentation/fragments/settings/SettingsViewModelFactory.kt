package com.mapx.kosten.mosimpa.presentation.fragments.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mapx.kosten.mosimpa.domain.interactors.patient.GetPatientsUseCase

class SettingsViewModelFactory (
    private val getPatientsUseCase: GetPatientsUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SettingsViewModel(getPatientsUseCase) as T
    }
}