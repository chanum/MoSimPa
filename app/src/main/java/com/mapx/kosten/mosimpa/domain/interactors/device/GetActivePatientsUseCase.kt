package com.mapx.kosten.mosimpa.domain.interactors.device

import com.mapx.kosten.mosimpa.domain.data.SensorsRepository

class GetActivePatientsUseCase (
    private val sensorsRepository: SensorsRepository
) {
    // suspend fun invoke() = sensorsRepository.getActivePatients()
}
