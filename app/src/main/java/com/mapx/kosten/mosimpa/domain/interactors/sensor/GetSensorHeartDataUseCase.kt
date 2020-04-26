package com.mapx.kosten.mosimpa.domain.interactors.sensor

import com.mapx.kosten.mosimpa.domain.data.SensorsRepository
import com.mapx.kosten.mosimpa.domain.entites.InternmentEntity
import com.mapx.kosten.mosimpa.domain.entites.PatientEntity

class GetSensorHeartDataUseCase (
    private val sensorsRepository: SensorsRepository
) {
    fun invoke(internment: InternmentEntity) = sensorsRepository.getHeartData(internment)
}
