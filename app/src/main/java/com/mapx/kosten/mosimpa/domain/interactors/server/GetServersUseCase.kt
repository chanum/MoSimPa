package com.mapx.kosten.mosimpa.domain.interactors.server

import com.mapx.kosten.mosimpa.domain.data.ServersRepository

class GetServersUseCase(
    private val serversRepository: ServersRepository
) {
    fun invoke() = serversRepository.getAll()
}