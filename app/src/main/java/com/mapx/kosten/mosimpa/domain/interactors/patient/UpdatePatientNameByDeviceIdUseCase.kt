package com.mapx.kosten.mosimpa.domain.interactors.patient

import com.mapx.kosten.mosimpa.domain.entites.PatientEntity
import com.mapx.kosten.mosimpa.domain.data.PatientsRepository

class UpdatePatientNameByDeviceIdUseCase (
    private val patientsRepository: PatientsRepository
) {
    suspend fun invoke(patient: PatientEntity) = patientsRepository.updateNameByDeviceId(patient)
}