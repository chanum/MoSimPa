package com.mapx.kosten.mosimpa.di.modules.patients

import com.mapx.kosten.mosimpa.domain.data.PatientsRepository
import com.mapx.kosten.mosimpa.domain.data.SensorsRepository
import com.mapx.kosten.mosimpa.domain.interactors.device.ObserveDevicesUseCase
import com.mapx.kosten.mosimpa.domain.interactors.device.SubscribeToAllDevices
import com.mapx.kosten.mosimpa.domain.interactors.patient.GetPatientsUseCase
import com.mapx.kosten.mosimpa.domain.interactors.patient.ObservePatientsUseCase
import com.mapx.kosten.mosimpa.domain.interactors.patient.SavePatientUseCase
import com.mapx.kosten.mosimpa.domain.interactors.sensor.ConnectClientMqttUseCase
import com.mapx.kosten.mosimpa.presentation.fragments.patients.PatientsViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class PatientsModule {

    @Provides
    fun provideGetPatientsUseCase(patientsRepository: PatientsRepository): GetPatientsUseCase {
        return GetPatientsUseCase(
            patientsRepository
        )
    }

    @Provides
    fun provideConnectClientMqttUseCase(sensorsRepository: SensorsRepository): ConnectClientMqttUseCase {
        return ConnectClientMqttUseCase(
            sensorsRepository
        )
    }

    @Provides
    fun provideSubscribeToAllDevices(sensorsRepository: SensorsRepository): SubscribeToAllDevices {
        return SubscribeToAllDevices(
            sensorsRepository
        )
    }

    @Provides
    fun provideObservePatientsUseCase(patientsRepository: PatientsRepository): ObservePatientsUseCase {
        return ObservePatientsUseCase(
            patientsRepository
        )
    }

    @Provides
    fun provideObserveDevicesUseCase(sensorsRepository: SensorsRepository): ObserveDevicesUseCase {
        return ObserveDevicesUseCase(
            sensorsRepository
        )
    }

    @Provides
    fun provideSavePatientUseCase(patientsRepository: PatientsRepository): SavePatientUseCase {
        return SavePatientUseCase(
            patientsRepository
        )
    }

    @Provides
    fun providePatientsViewModelFactory(
        getPatientsUseCase: GetPatientsUseCase,
        connectClientMqttUseCase: ConnectClientMqttUseCase,
        subscribeToAllDevices: SubscribeToAllDevices,
        observePatientsUseCase: ObservePatientsUseCase,
        observeDevicesUseCase: ObserveDevicesUseCase,
        savePatientUseCase: SavePatientUseCase
    ): PatientsViewModelFactory {
        return PatientsViewModelFactory(
            getPatientsUseCase,
            connectClientMqttUseCase,
            subscribeToAllDevices,
            observePatientsUseCase,
            observeDevicesUseCase,
            savePatientUseCase
        )
    }

}