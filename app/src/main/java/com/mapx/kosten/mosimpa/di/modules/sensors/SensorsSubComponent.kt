package com.mapx.kosten.mosimpa.di.modules.sensors

import com.mapx.kosten.mosimpa.presentation.fragments.sensors.SensorsFragment
import dagger.Subcomponent

@SensorsScope
@Subcomponent(modules = [SensorsModule::class])
interface SensorsSubComponent {
    fun inject(sensorsFragment: SensorsFragment)
}