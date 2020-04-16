package com.mapx.kosten.mosimpa.domain.interactors.patient

import com.mapx.kosten.mosimpa.domain.data.PatientsRepository

class GetDeviceIdByPatientId (
    private val patientsRepository: PatientsRepository
) {
    suspend fun invoke(id: Long) = patientsRepository.getDeviceIdByPatientId(id)
}