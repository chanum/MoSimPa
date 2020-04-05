package com.mapx.kosten.mosimpa.di.modules.settings

import com.mapx.kosten.mosimpa.domain.data.PatientsRepository
import com.mapx.kosten.mosimpa.domain.interactors.patient.GetPatientsUseCase
import com.mapx.kosten.mosimpa.presentation.common.ASyncTransformer
import com.mapx.kosten.mosimpa.presentation.fragments.settings.SettingsViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class SettingsModule {

    @Provides
    fun provideGetPatientsUseCase(patientsRepository: PatientsRepository): GetPatientsUseCase {
        return GetPatientsUseCase(
            ASyncTransformer(),
            patientsRepository
        )
    }

    @Provides
    fun provideSettingsViewModelFactory(
        getPatientsUseCase: GetPatientsUseCase
    ): SettingsViewModelFactory {
        return SettingsViewModelFactory(getPatientsUseCase)
    }

}