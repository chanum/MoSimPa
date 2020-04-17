package com.mapx.kosten.mosimpa.domain.interactors.patient

import com.mapx.kosten.mosimpa.domain.data.PatientsRepository

class DeletePatientUseCase (
    private val patientsRepository: PatientsRepository
) {
    suspend fun invoke(id: Long) = patientsRepository.deletePatientById(id)
}
