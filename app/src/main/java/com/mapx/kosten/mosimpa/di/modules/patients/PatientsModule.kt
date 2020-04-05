package com.mapx.kosten.mosimpa.di.modules.patients

import com.mapx.kosten.mosimpa.domain.data.PatientsRepository
import com.mapx.kosten.mosimpa.domain.interactors.patient.GetPatientsUseCase
import com.mapx.kosten.mosimpa.domain.interactors.patient.SavePatientUseCase
import com.mapx.kosten.mosimpa.presentation.common.ASyncTransformer
import com.mapx.kosten.mosimpa.presentation.fragments.patients.PatientsViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class PatientsModule {

    @Provides
    fun provideGetPatientsUseCase(patientsRepository: PatientsRepository): GetPatientsUseCase {
        return GetPatientsUseCase(
            ASyncTransformer(),
            patientsRepository
        )
    }

    @Provides
    fun providePatientsViewModelFactory(
        getPatientsUseCase: GetPatientsUseCase
    ): PatientsViewModelFactory {
        return PatientsViewModelFactory(getPatientsUseCase)
    }

}