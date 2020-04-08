package com.mapx.kosten.mosimpa.domain.interactors.sensor

import com.mapx.kosten.mosimpa.domain.data.SensorsRepository

class GetSensorSpo2DataUseCase (
    private val sensorsRepository: SensorsRepository
) {
    fun invoke() = sensorsRepository.getSpo2Data()
}
