package com.mapx.kosten.mosimpa.di.modules.login

import com.mapx.kosten.mosimpa.domain.data.ServersRepository
import com.mapx.kosten.mosimpa.domain.data.SettingsRepository
import com.mapx.kosten.mosimpa.domain.interactors.server.*
import com.mapx.kosten.mosimpa.presentation.viewmodels.LoginViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class LoginModule {

    @Provides
    fun provideSaveServerUseCase(serversRepository: ServersRepository): SaveServerUseCase {
        return SaveServerUseCase(
            serversRepository
        )
    }

    @Provides
    fun provideGetServerUseCase(serversRepository: ServersRepository): GetServersUseCase {
        return GetServersUseCase(
            serversRepository
        )
    }

    @Provides
    fun provideDeleteServerUseCase(serversRepository: ServersRepository): DeleteServerUseCase {
        return DeleteServerUseCase(
            serversRepository
        )
    }

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
        saveServerUseCase: SaveServerUseCase,
        getServersUseCase: GetServersUseCase,
        deleteServerUseCase: DeleteServerUseCase,
        getBrokerConfigUseCase: GetBrokerConfigUseCase,
        setBrokerConfigUseCase: SetBrokerConfigUseCase
    ): LoginViewModelFactory {
        return LoginViewModelFactory(
            saveServerUseCase,
            getServersUseCase,
            deleteServerUseCase,
            getBrokerConfigUseCase,
            setBrokerConfigUseCase
        )
    }
}