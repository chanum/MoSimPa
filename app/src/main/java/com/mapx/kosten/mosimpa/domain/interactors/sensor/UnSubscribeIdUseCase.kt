package com.mapx.kosten.mosimpa.domain.interactors.sensor

import com.mapx.kosten.mosimpa.domain.data.InternmentsRepository
import com.mapx.kosten.mosimpa.domain.entites.InternmentEntity

class UnSubscribeIdUseCase(
    private val internmentsRepository: InternmentsRepository
) {
    suspend fun invoke(internment: InternmentEntity) = internmentsRepository.unSubscribeId(internment)
}