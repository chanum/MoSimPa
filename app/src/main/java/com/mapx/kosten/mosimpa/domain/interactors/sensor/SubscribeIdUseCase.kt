package com.mapx.kosten.mosimpa.domain.interactors.sensor

import com.mapx.kosten.mosimpa.domain.data.SensorsRepository

class SubscribeIdUseCase(
private val sensorsRepository: SensorsRepository
) {
    suspend fun invoke(id: Long) = sensorsRepository.subscribeId(id)
}