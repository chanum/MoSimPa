package com.mapx.kosten.mosimpa.domain.interactors.sensor

import com.mapx.kosten.mosimpa.domain.data.SensorsRepository

class GetSensorO2DataUseCase (
    private val sensorsRepository: SensorsRepository
) {
    fun invoke(id: Long) = sensorsRepository.getO2Data(id)
}
