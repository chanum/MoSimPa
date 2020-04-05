package com.mapx.kosten.mosimpa.presentation.fragments.patients

import com.mapx.kosten.mosimpa.domain.PatientEntity

data class PatientsViewState(
    val isLoading: Boolean = true,
    val isEmpty: Boolean = true,
    val patientEntities: List<PatientEntity>? = null
)