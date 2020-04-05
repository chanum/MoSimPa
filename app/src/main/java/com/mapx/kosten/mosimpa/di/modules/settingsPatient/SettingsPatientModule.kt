package com.mapx.kosten.mosimpa.di.modules.settingsPatient

import com.mapx.kosten.mosimpa.domain.data.PatientsRepository
import com.mapx.kosten.mosimpa.domain.interactors.patient.DeletePatientUseCase
import com.mapx.kosten.mosimpa.domain.interactors.patient.GetPatientUseCase
import com.mapx.kosten.mosimpa.domain.interactors.patient.SavePatientUseCase
import com.mapx.kosten.mosimpa.presentation.common.ASyncTransformer
import com.mapx.kosten.mosimpa.presentation.fragments.settingsPatient.SettingsPatientViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class SettingsPatientModule {

    @Provides
    fun provideGetPatientUseCase(patientsRepository: PatientsRepository): GetPatientUseCase {
        return GetPatientUseCase(
            ASyncTransformer(),
            patientsRepository
        )
    }

    @Provides
    fun provideSavePatientUseCase(patientsRepository: PatientsRepository): SavePatientUseCase {
        return SavePatientUseCase(
            ASyncTransformer(),
            patientsRepository
        )
    }

    @Provides
    fun provideDeletePatientUseCase(patientsRepository: PatientsRepository): DeletePatientUseCase {
        return DeletePatientUseCase(
            ASyncTransformer(),
            patientsRepository
        )
    }

    @Provides
    fun provideSettingsPatientViewModelFactory(
        savePatientUseCase: SavePatientUseCase,
        getPatientUseCase: GetPatientUseCase,
        deletePatientUseCase: DeletePatientUseCase
    ): SettingsPatientViewModelFactory {
        return SettingsPatientViewModelFactory(
            savePatientUseCase,
            getPatientUseCase,
            deletePatientUseCase
        )
    }

}