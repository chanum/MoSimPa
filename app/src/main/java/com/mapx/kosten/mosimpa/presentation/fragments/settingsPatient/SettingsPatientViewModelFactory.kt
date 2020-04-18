package com.mapx.kosten.mosimpa.presentation.fragments.settingsPatient

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mapx.kosten.mosimpa.domain.interactors.patient.DeletePatientUseCase
import com.mapx.kosten.mosimpa.domain.interactors.patient.GetPatientUseCase
import com.mapx.kosten.mosimpa.domain.interactors.patient.SavePatientUseCase
import com.mapx.kosten.mosimpa.domain.interactors.patient.UpdatePatientNameByDeviceIdUseCase

class SettingsPatientViewModelFactory(
    private val savePatientUseCase: SavePatientUseCase,
    private val getPatientUseCase: GetPatientUseCase,
    private val deletePatientUseCase: DeletePatientUseCase,
    private val updatePatientNameByDeviceIdUseCase: UpdatePatientNameByDeviceIdUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SettingsPatientViewModel(
            savePatientUseCase,
            getPatientUseCase,
            deletePatientUseCase,
            updatePatientNameByDeviceIdUseCase
        ) as T
    }
}