package com.mapx.kosten.mosimpa.di

import com.mapx.kosten.mosimpa.di.modules.AppModule
import com.mapx.kosten.mosimpa.di.modules.DataModule
import com.mapx.kosten.mosimpa.di.modules.NetworkModule
import com.mapx.kosten.mosimpa.di.modules.patients.PatientsModule
import com.mapx.kosten.mosimpa.di.modules.patients.PatientsSubComponent
import com.mapx.kosten.mosimpa.di.modules.sensors.SensorsModule
import com.mapx.kosten.mosimpa.di.modules.sensors.SensorsSubComponent
import com.mapx.kosten.mosimpa.di.modules.settings.SettingsModule
import com.mapx.kosten.mosimpa.di.modules.settings.SettingsSubComponent
import com.mapx.kosten.mosimpa.di.modules.settingsPatient.SettingsPatientModule
import com.mapx.kosten.mosimpa.di.modules.settingsPatient.SettingsPatientSubComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    (AppModule::class),
    (NetworkModule::class),
    (DataModule::class)
])

interface MainComponent {
    fun plus(patientsModule: PatientsModule): PatientsSubComponent
    fun plus(settingsModule: SettingsModule): SettingsSubComponent
    fun plus(settingsPatientModule: SettingsPatientModule): SettingsPatientSubComponent
    fun plus(sensorsModule: SensorsModule): SensorsSubComponent
}