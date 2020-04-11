package com.mapx.kosten.mosimpa.domain.interactors.sensor

import com.mapx.kosten.mosimpa.domain.data.SensorsRepository

class UnSubscribeIdUseCase(
    private val sensorsRepository: SensorsRepository
) {
    fun invoke(id: Long) = sensorsRepository.unSubscribeId(id)
}