package com.mapx.kosten.mosimpa.di.modules.settingsPatient

import com.mapx.kosten.mosimpa.presentation.fragments.settingsPatient.SettingsPatientFragment
import dagger.Subcomponent

@SettingsPatientScope
@Subcomponent(modules = [SettingsPatientModule::class])
interface SettingsPatientSubComponent {
    fun inject(settingsPatientFragment: SettingsPatientFragment)
}