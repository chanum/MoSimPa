package com.mapx.kosten.mosimpa.presentation.common

import android.app.Application
import com.mapx.kosten.mosimpa.di.DaggerMainComponent
import com.mapx.kosten.mosimpa.di.MainComponent
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

class App: Application() {

    lateinit var mainComponent: MainComponent
    private var patientsSubComponent: PatientsSubComponent? = null
    private var settingsSubComponent: SettingsSubComponent? = null
    private var settingsPatientSubComponent: SettingsPatientSubComponent? = null
    private var sensorsSubComponent: SensorsSubComponent? = null

    override fun onCreate() {
        super.onCreate()
        initDependencies()
    }

    private fun initDependencies() {
        mainComponent = DaggerMainComponent.builder()
            .appModule(AppModule(applicationContext))
            .networkModule(NetworkModule())
            .dataModule(DataModule())
            .build()
    }

    // Patients
    fun createPatientsComponent(): PatientsSubComponent {
        patientsSubComponent = mainComponent.plus(PatientsModule())
        return patientsSubComponent!!
    }

    fun releasePatientsComponent() {
        patientsSubComponent = null
    }

    // Settings
    fun createSettingsComponent(): SettingsSubComponent {
        settingsSubComponent = mainComponent.plus(SettingsModule())
        return settingsSubComponent!!
    }

    fun releaseSettingsComponent() {
        settingsSubComponent = null
    }

    // Settings Patient
    fun createSettingsPatientComponent(): SettingsPatientSubComponent {
        settingsPatientSubComponent = mainComponent.plus(SettingsPatientModule())
        return settingsPatientSubComponent!!
    }

    fun releaseSettingsPatientComponent() {
        settingsPatientSubComponent = null
    }

    // Sensors
    fun createSensorsComponent(): SensorsSubComponent {
        sensorsSubComponent = mainComponent.plus(SensorsModule())
        return sensorsSubComponent!!
    }

    fun releaseSensorsComponent() {
        sensorsSubComponent = null
    }

}