package com.mapx.kosten.mosimpa.domain.interactors.patient

import com.mapx.kosten.mosimpa.domain.entites.PatientEntity
import com.mapx.kosten.mosimpa.domain.data.PatientsRepository

class SavePatientUseCase (
    private val patientsRepository: PatientsRepository
) {
    suspend fun invoke(patient: PatientEntity) = patientsRepository.savePatient(patient)
}