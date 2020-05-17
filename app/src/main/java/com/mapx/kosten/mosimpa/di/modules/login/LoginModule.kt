package com.mapx.kosten.mosimpa.di.modules.login

import com.mapx.kosten.mosimpa.domain.data.SettingsRepository
import com.mapx.kosten.mosimpa.domain.interactors.settings.GetBrokerConfigUseCase
import com.mapx.kosten.mosimpa.domain.interactors.settings.SetBrokerConfigUseCase
import com.mapx.kosten.mosimpa.presentation.viewmodels.LoginViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class LoginModule {
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
    fun provideLoginViewModelFactory(
        getBrokerConfigUseCase: GetBrokerConfigUseCase,
        setBrokerConfigUseCase: SetBrokerConfigUseCase
    ): LoginViewModelFactory {
        return LoginViewModelFactory(
            getBrokerConfigUseCase,
            setBrokerConfigUseCase
        )
    }
}