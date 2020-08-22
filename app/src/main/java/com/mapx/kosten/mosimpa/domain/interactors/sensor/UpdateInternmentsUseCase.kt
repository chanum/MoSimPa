package com.mapx.kosten.mosimpa.domain.interactors.sensor

import com.mapx.kosten.mosimpa.domain.data.InternmentsRepository

class UpdateInternmentsUseCase(
private val internmentsRepository: InternmentsRepository
) {
    fun invoke() = internmentsRepository.updateInternments()
}