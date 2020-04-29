package com.mapx.kosten.mosimpa.domain.interactors.sensor

import com.mapx.kosten.mosimpa.domain.data.SensorsRepository
import com.mapx.kosten.mosimpa.domain.entites.InternmentEntity

class UpdateInternmentsUseCase(
private val sensorsRepository: SensorsRepository
) {
    fun invoke() = sensorsRepository.updateInternments()
}