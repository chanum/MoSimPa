package com.mapx.kosten.mosimpa.di.modules.patients

import com.mapx.kosten.mosimpa.presentation.fragments.patients.PatientsViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class PatientsModule {

    @Provides
    fun providePatientsViewModelFactory(
    ): PatientsViewModelFactory {
        return PatientsViewModelFactory()
    }

}