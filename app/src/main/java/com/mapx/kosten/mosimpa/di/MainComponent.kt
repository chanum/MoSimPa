package com.mapx.kosten.mosimpa.di

import com.mapx.kosten.mosimpa.di.modules.AppModule
import com.mapx.kosten.mosimpa.di.modules.DataModule
import com.mapx.kosten.mosimpa.di.modules.NetworkModule
import com.mapx.kosten.mosimpa.di.modules.internments.InternmentsModule
import com.mapx.kosten.mosimpa.di.modules.internments.InternmentsSubComponent
import com.mapx.kosten.mosimpa.di.modules.sensors.SensorsModule
import com.mapx.kosten.mosimpa.di.modules.sensors.SensorsSubComponent
import com.mapx.kosten.mosimpa.di.modules.settings.SettingsModule
import com.mapx.kosten.mosimpa.di.modules.settings.SettingsSubComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    (AppModule::class),
    (NetworkModule::class),
    (DataModule::class)
])

interface MainComponent {
    fun plus(internmentsModule: InternmentsModule): InternmentsSubComponent
    fun plus(settingsModule: SettingsModule): SettingsSubComponent
    fun plus(sensorsModule: SensorsModule): SensorsSubComponent
}