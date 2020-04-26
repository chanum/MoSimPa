package com.mapx.kosten.mosimpa.domain.interactors.patient

import com.mapx.kosten.mosimpa.domain.data.InternmentsRepository

class GetInternmentsUseCase (
    private val internmentsRepository: InternmentsRepository
) {
    fun invoke() = internmentsRepository.getAll()
}
