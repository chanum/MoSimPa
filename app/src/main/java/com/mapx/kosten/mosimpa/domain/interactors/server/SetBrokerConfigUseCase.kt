package com.mapx.kosten.mosimpa.domain.interactors.server

import com.mapx.kosten.mosimpa.domain.data.SettingsRepository
import com.mapx.kosten.mosimpa.domain.entites.ServerEntity

class SetBrokerConfigUseCase(
    private val settingsRepository: SettingsRepository
) {
    fun invoke(server: ServerEntity) = settingsRepository.setCurrentServer(server)
}