package com.mapx.kosten.mosimpa.domain.interactors.patient

import com.mapx.kosten.mosimpa.domain.data.InternmentsRepository

class GetDeviceIdByInternmentId (
    private val internmentsRepository: InternmentsRepository
) {
    suspend fun invoke(id: Long) = internmentsRepository.getDeviceIdById(id)
}