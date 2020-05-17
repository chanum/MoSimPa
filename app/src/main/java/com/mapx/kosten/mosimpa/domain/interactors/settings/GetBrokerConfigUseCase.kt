package com.mapx.kosten.mosimpa.domain.interactors.settings

import com.mapx.kosten.mosimpa.domain.data.SettingsRepository

class GetBrokerConfigUseCase(
    private val settingsRepository: SettingsRepository
) {
    fun invoke() = settingsRepository.getCurrentServer()
}