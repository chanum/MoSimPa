package com.mapx.kosten.mosimpa.presentation.fragments.patients

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mapx.kosten.mosimpa.domain.interactors.patient.GetPatientsUseCase

class PatientsViewModelFactory (
    private val getPatientsUseCase: GetPatientsUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PatientsViewModel(getPatientsUseCase) as T
    }
}