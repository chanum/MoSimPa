package com.mapx.kosten.mosimpa.domain.interactors.settings

import com.mapx.kosten.mosimpa.domain.data.SettingsRepository

class SetBrokerConfigUseCase(
    private val settingsRepository: SettingsRepository
) {
    suspend fun invoke(ip: String) = settingsRepository.setBrokerIp(ip)
}