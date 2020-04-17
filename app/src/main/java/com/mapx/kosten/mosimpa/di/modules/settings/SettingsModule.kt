package com.mapx.kosten.mosimpa.di.modules.settings

import com.mapx.kosten.mosimpa.domain.data.PatientsRepository
import com.mapx.kosten.mosimpa.domain.data.SettingsRepository
import com.mapx.kosten.mosimpa.domain.interactors.patient.GetPatientsUseCase
import com.mapx.kosten.mosimpa.domain.interactors.settings.GetBrokerConfigUseCase
import com.mapx.kosten.mosimpa.domain.interactors.settings.SetBrokerConfigUseCase
import com.mapx.kosten.mosimpa.presentation.fragments.settings.SettingsViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class SettingsModule {

    @Provides
    fun provideGetPatientsUseCase(patientsRepository: PatientsRepository): GetPatientsUseCase {
        return GetPatientsUseCase(
            patientsRepository
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
    fun provideSettingsViewModelFactory(
        getPatientsUseCase: GetPatientsUseCase,
        getBrokerConfigUseCase: GetBrokerConfigUseCase,
        setBrokerConfigUseCase: SetBrokerConfigUseCase
    ): SettingsViewModelFactory {
        return SettingsViewModelFactory(
            getPatientsUseCase,
            getBrokerConfigUseCase,
            setBrokerConfigUseCase
        )
    }

}