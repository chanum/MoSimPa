package com.mapx.kosten.mosimpa.presentation.fragments.settings

import com.mapx.kosten.mosimpa.domain.PatientEntity

data class SettingsViewState(
    val isLoading: Boolean = true,
    val isEmpty: Boolean = true,
    val patients: List<PatientEntity>? = null
)