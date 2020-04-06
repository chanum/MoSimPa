package com.mapx.kosten.mosimpa.di.modules.sensors

import com.mapx.kosten.mosimpa.presentation.fragments.sensors.SensorsViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class SensorsModule {

    @Provides
    fun provideSensorsViewModelFactory(
    ): SensorsViewModelFactory {
        return SensorsViewModelFactory()
    }

}