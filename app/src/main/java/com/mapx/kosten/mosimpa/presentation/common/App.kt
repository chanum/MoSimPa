package com.mapx.kosten.mosimpa.presentation.common

import android.app.Application
import com.mapx.kosten.mosimpa.di.DaggerMainComponent
import com.mapx.kosten.mosimpa.di.MainComponent
import com.mapx.kosten.mosimpa.di.modules.AppModule
import com.mapx.kosten.mosimpa.di.modules.DataModule
import com.mapx.kosten.mosimpa.di.modules.patients.PatientsModule
import com.mapx.kosten.mosimpa.di.modules.patients.PatientsSubComponent

class App: Application() {

    lateinit var mainComponent: MainComponent
    private var patientsSubComponent: PatientsSubComponent? = null

    override fun onCreate() {
        super.onCreate()
        initDependencies()
    }

    private fun initDependencies() {
        mainComponent = DaggerMainComponent.builder()
            .appModule(AppModule(applicationContext))
            // .networkModule(NetworkModule(ATHELING_SERVER_URL))
            .dataModule(DataModule())
            .build()
    }

    fun createPatientsComponent(): PatientsSubComponent {
        patientsSubComponent = mainComponent.plus(PatientsModule())
        return patientsSubComponent!!
    }

    fun releasePatientsComponent() {
        patientsSubComponent = null
    }
}