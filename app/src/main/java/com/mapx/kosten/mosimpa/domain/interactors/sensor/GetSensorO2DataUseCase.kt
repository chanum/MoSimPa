package com.mapx.kosten.mosimpa.domain.interactors.sensor

import com.mapx.kosten.mosimpa.domain.data.SensorsRepository

class GetSensorO2DataUseCase (
    private val sensorsRepository: SensorsRepository
) {
    fun invoke() = sensorsRepository.getO2Data()
}
