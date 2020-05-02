package com.mapx.kosten.mosimpa.di.modules.settings

import com.mapx.kosten.mosimpa.domain.data.SettingsRepository
import com.mapx.kosten.mosimpa.domain.interactors.settings.GetBrokerConfigUseCase
import com.mapx.kosten.mosimpa.domain.interactors.settings.SetBrokerConfigUseCase
import com.mapx.kosten.mosimpa.presentation.viewmodels.SettingsViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class SettingsModule {

    @Provides
    fun provideGetBrokerConfigUseCase(settingsRepository: SettingsRepository): GetBrokerConfigUseCase {
        return GetBrokerConfigUseCase(
            settingsRepository
        )
    }

    @Provides
    fun provideSetBrokerConfigUseCase(settingsRepository: SettingsRepository): SetBrokerConfigUseCase {
        return SetBrokerConfigUseCase(
            settingsRepository
        )
    }

    @Provides
    fun provideSettingsViewModelFactory(
        getBrokerConfigUseCase: GetBrokerConfigUseCase,
        setBrokerConfigUseCase: SetBrokerConfigUseCase
    ): SettingsViewModelFactory {
        return SettingsViewModelFactory(
            getBrokerConfigUseCase,
            setBrokerConfigUseCase
        )
    }

}