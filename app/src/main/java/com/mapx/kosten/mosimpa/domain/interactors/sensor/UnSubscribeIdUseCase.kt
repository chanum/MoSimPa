package com.mapx.kosten.mosimpa.domain.interactors.sensor

import com.mapx.kosten.mosimpa.domain.data.SensorsRepository
import com.mapx.kosten.mosimpa.domain.entites.InternmentEntity

class UnSubscribeIdUseCase(
    private val sensorsRepository: SensorsRepository
) {
    suspend fun invoke(internment: InternmentEntity) = sensorsRepository.unSubscribeId(internment)
}