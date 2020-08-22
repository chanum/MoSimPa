package com.mapx.kosten.mosimpa.domain.interactors.device

import com.mapx.kosten.mosimpa.domain.data.InternmentsRepository

class SubscribeToAllDevices (
    private val internmentsRepository: InternmentsRepository
) {
    suspend fun invoke() = internmentsRepository.subscribeToAll()
}