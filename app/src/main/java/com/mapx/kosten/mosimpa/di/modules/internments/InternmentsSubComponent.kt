package com.mapx.kosten.mosimpa.di.modules.internments

import com.mapx.kosten.mosimpa.presentation.fragments.internments.InternmentsFragment
import com.mapx.kosten.mosimpa.presentation.fragments.sensors.SensorsFragment
import dagger.Subcomponent

@InternmentsScope
@Subcomponent(modules = [InternmentsModule::class])
interface InternmentsSubComponent {
    fun inject(internmentsFragment: InternmentsFragment)
    fun inject(sensorsFragment: SensorsFragment)
}