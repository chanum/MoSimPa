package com.mapx.kosten.mosimpa.domain.interactors.sensor

import com.mapx.kosten.mosimpa.domain.data.InternmentsRepository

class GetSensorO2DataUseCase (
    private val internmentsRepository: InternmentsRepository
) {
    fun invoke() = internmentsRepository.getO2Data()
}
