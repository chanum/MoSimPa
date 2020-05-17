package com.mapx.kosten.mosimpa.domain.interactors.server

import com.mapx.kosten.mosimpa.domain.data.ServersRepository
import com.mapx.kosten.mosimpa.domain.entites.ServerEntity

class SaveServerUseCase(
    private val serversRepository: ServersRepository
) {
    suspend fun invoke(server: ServerEntity) = serversRepository.saveServer(server)
}