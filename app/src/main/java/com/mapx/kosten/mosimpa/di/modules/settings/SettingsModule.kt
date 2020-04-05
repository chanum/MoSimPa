package com.mapx.kosten.mosimpa.di.modules.settings

import com.mapx.kosten.mosimpa.presentation.fragments.settings.SettingsViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class SettingsModule {

    @Provides
    fun provideSettingsViewModelFactory(
    ): SettingsViewModelFactory {
        return SettingsViewModelFactory()
    }

}