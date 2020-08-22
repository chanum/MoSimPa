package com.mapx.kosten.mosimpa.domain.interactors.sensor

import com.mapx.kosten.mosimpa.domain.data.InternmentsRepository
import com.mapx.kosten.mosimpa.domain.entites.InternmentEntity

class SubscribeIdUseCase(
private val internmentsRepository: InternmentsRepository
) {
    fun invoke(internment: InternmentEntity) = internmentsRepository.subscribeId(internment)
}