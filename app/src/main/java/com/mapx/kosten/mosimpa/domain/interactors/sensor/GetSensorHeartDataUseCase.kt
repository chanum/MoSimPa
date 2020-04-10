package com.mapx.kosten.mosimpa.domain.interactors.sensor

import com.mapx.kosten.mosimpa.domain.data.SensorsRepository

class GetSensorHeartDataUseCase (
    private val sensorsRepository: SensorsRepository
) {
    fun invoke() = sensorsRepository.getHeartData()
}
