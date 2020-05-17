package com.mapx.kosten.mosimpa.domain.interactors.server

import com.mapx.kosten.mosimpa.domain.data.ServersRepository
import com.mapx.kosten.mosimpa.domain.entites.ServerEntity

class DeleteServerUseCase(
    private val serversRepository: ServersRepository
) {
    suspend fun invoke(server: ServerEntity) = serversRepository.deleteServer(server)
}