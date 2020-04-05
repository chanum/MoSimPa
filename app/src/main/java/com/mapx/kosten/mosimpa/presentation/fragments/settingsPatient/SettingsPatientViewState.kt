package com.mapx.kosten.mosimpa.presentation.fragments.settingsPatient

import com.mapx.kosten.mosimpa.domain.PatientEntity
import com.mapx.kosten.mosimpa.presentation.fragments.settingsPatient.SettingsPatientFragment.Companion.SAVE_OK

data class SettingsPatientViewState (
    val isLoading: Boolean = true,
    val saveStatus: Int = SAVE_OK,
    val close: Boolean = false,
    val patient: PatientEntity? = null
)