package com.mapx.kosten.mosimpa.domain.interactors.device

import com.mapx.kosten.mosimpa.domain.data.SensorsRepository

class ObserveDevicesUseCase (
    private val sensorsRepository: SensorsRepository
) {
    fun invoke() = sensorsRepository.observeDevices()
}