package com.mapx.kosten.mosimpa.domain.interactors.sensor

import com.mapx.kosten.mosimpa.domain.data.SensorsRepository
import com.mapx.kosten.mosimpa.domain.entites.InternmentEntity

class GetSensorBloodDataUseCase (
    private val sensorsRepository: SensorsRepository
) {
    fun invoke(internment: InternmentEntity) = sensorsRepository.getBloodData(internment)
}
