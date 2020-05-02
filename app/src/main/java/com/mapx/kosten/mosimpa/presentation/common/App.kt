package com.mapx.kosten.mosimpa.presentation.common

import android.app.Application
import com.mapx.kosten.mosimpa.di.DaggerMainComponent
import com.mapx.kosten.mosimpa.di.MainComponent
import com.mapx.kosten.mosimpa.di.modules.AppModule
import com.mapx.kosten.mosimpa.di.modules.DataModule
import com.mapx.kosten.mosimpa.di.modules.NetworkModule
import com.mapx.kosten.mosimpa.di.modules.internments.InternmentsModule
import com.mapx.kosten.mosimpa.di.modules.internments.InternmentsSubComponent
import com.mapx.kosten.mosimpa.di.modules.settings.SettingsModule
import com.mapx.kosten.mosimpa.di.modules.settings.SettingsSubComponent

class App: Application() {

    lateinit var mainComponent: MainComponent
    private var internmentsSubComponent: InternmentsSubComponent? = null
    private var settingsSubComponent: SettingsSubComponent? = null

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

    // Internments
    fun createInternmentsComponent(): InternmentsSubComponent {
        internmentsSubComponent = mainComponent.plus(InternmentsModule())
        return internmentsSubComponent!!
    }

    fun releaseInternmentsComponent() {
        internmentsSubComponent = null
    }

    // Settings
    fun createSettingsComponent(): SettingsSubComponent {
        settingsSubComponent = mainComponent.plus(SettingsModule())
        return settingsSubComponent!!
    }

    fun releaseSettingsComponent() {
        settingsSubComponent = null
    }

}