package com.mapx.kosten.mosimpa.domain.interactors.patient

import com.mapx.kosten.mosimpa.domain.data.PatientsRepository

class GetPatientsUseCase (
    private val patientsRepository: PatientsRepository
) {
    suspend fun invoke() = patientsRepository.getAllPatients()
}
