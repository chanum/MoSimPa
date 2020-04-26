package com.mapx.kosten.mosimpa.domain.interactors.sensor

import com.mapx.kosten.mosimpa.domain.data.SensorsRepository
import com.mapx.kosten.mosimpa.domain.entites.InternmentEntity
import com.mapx.kosten.mosimpa.domain.entites.PatientEntity

class GetSensorO2DataUseCase (
    private val sensorsRepository: SensorsRepository
) {
    fun invoke(internment: InternmentEntity) = sensorsRepository.getO2Data(internment)
}
