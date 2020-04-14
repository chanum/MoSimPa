package com.mapx.kosten.mosimpa.domain.interactors.device

import com.mapx.kosten.mosimpa.domain.data.SensorsRepository

class SubscribeToAllDevices (
    private val sensorsRepository: SensorsRepository
) {
    suspend fun invoke() = sensorsRepository.subscribeToAll()
}