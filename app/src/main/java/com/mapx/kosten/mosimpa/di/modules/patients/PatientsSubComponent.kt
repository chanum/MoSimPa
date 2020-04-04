package com.mapx.kosten.mosimpa.di.modules.patients

import com.mapx.kosten.mosimpa.presentation.fragments.patients.PatientsFragment
import dagger.Subcomponent

@PatientsScope
@Subcomponent(modules = [PatientsModule::class])
interface PatientsSubComponent {
    fun inject(patientsFragment: PatientsFragment)
}