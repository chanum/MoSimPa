package com.mapx.kosten.mosimpa.domain.interactors.sensor

import com.mapx.kosten.mosimpa.domain.data.SensorsRepository
import com.mapx.kosten.mosimpa.domain.entites.PatientEntity

class UnSubscribeIdUseCase(
    private val sensorsRepository: SensorsRepository
) {
    fun invoke(patient: PatientEntity) = sensorsRepository.unSubscribeId(patient)
}