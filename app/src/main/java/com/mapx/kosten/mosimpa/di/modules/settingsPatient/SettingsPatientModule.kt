package com.mapx.kosten.mosimpa.di.modules.settingsPatient

import com.mapx.kosten.mosimpa.domain.data.PatientsRepository
import com.mapx.kosten.mosimpa.domain.interactors.patient.SavePatientUseCase
import com.mapx.kosten.mosimpa.presentation.common.ASyncTransformer
import com.mapx.kosten.mosimpa.presentation.fragments.settingsPatient.SettingsPatientViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class SettingsPatientModule {

    @Provides
    fun provideSavePatientUseCase(patientsRepository: PatientsRepository): SavePatientUseCase {
        return SavePatientUseCase(
            ASyncTransformer(),
            patientsRepository
        )
    }

    @Provides
    fun provideSettingsPatientViewModelFactory(
        savePatientUseCase: SavePatientUseCase
    ): SettingsPatientViewModelFactory {
        return SettingsPatientViewModelFactory(savePatientUseCase)
    }

}