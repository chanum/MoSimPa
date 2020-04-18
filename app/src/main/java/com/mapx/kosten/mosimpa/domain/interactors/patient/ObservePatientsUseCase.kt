package com.mapx.kosten.mosimpa.domain.interactors.patient

import com.mapx.kosten.mosimpa.domain.data.PatientsRepository

class ObservePatientsUseCase (
    private val patientsRepository: PatientsRepository
) {
    fun invoke() = patientsRepository.observePatients()
}
