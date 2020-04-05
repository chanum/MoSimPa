package com.mapx.kosten.mosimpa.presentation.fragments.settings

import com.mapx.kosten.mosimpa.domain.Patient

data class SettingsViewState(
    val isLoading: Boolean = true,
    val isEmpty: Boolean = true,
    val patients: List<Patient>? = null
)