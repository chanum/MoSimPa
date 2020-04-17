package com.mapx.kosten.mosimpa.domain.interactors.patient

import com.mapx.kosten.mosimpa.domain.data.PatientsRepository

class GetPatientUseCase (
    private val patientsRepository: PatientsRepository
) {
    suspend fun invoke(id: Long) = patientsRepository.getPatientsById(id)
}
