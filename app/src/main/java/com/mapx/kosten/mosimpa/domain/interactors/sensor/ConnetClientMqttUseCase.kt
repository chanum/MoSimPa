package com.mapx.kosten.mosimpa.domain.interactors.sensor

import com.mapx.kosten.mosimpa.domain.data.SensorsRepository

class ConnetClientMqttUseCase(
    private val sensorsRepository: SensorsRepository
) {
    fun invoke(id: Long) = sensorsRepository.connectMqtt()
}