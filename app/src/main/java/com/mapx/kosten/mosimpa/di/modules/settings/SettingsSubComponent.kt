package com.mapx.kosten.mosimpa.di.modules.settings

import com.mapx.kosten.mosimpa.presentation.fragments.settings.SettingsFragment
import dagger.Subcomponent

@SettingsScope
@Subcomponent(modules = [SettingsModule::class])
interface SettingsSubComponent {
    fun inject(settingsFragment: SettingsFragment)
}