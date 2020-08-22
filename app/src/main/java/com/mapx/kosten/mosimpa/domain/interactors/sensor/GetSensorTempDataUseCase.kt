package com.mapx.kosten.mosimpa.domain.interactors.sensor

import com.mapx.kosten.mosimpa.domain.data.InternmentsRepository

class GetSensorTempDataUseCase (
    private val internmentsRepository: InternmentsRepository
) {
    fun invoke() = internmentsRepository.getTempData()
}
