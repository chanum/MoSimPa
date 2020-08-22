package com.mapx.kosten.mosimpa.domain.interactors.sensor

import com.mapx.kosten.mosimpa.domain.data.InternmentsRepository

class ConnectClientMqttUseCase(
    private val internmentsRepository: InternmentsRepository
) {
    suspend fun invoke(mac: String) = internmentsRepository.connectMqtt(mac)
}