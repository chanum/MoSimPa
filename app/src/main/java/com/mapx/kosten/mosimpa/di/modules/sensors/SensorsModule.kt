package com.mapx.kosten.mosimpa.di.modules.sensors

import com.mapx.kosten.mosimpa.domain.data.InternmentsRepository
import com.mapx.kosten.mosimpa.domain.data.PatientsRepository
import com.mapx.kosten.mosimpa.domain.data.SensorsRepository
import com.mapx.kosten.mosimpa.domain.interactors.patient.GetDeviceIdByInternmentId
import com.mapx.kosten.mosimpa.domain.interactors.patient.GetDeviceIdByPatientId
import com.mapx.kosten.mosimpa.domain.interactors.sensor.*
import com.mapx.kosten.mosimpa.presentation.fragments.sensors.SensorsViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class SensorsModule {

    @Provides
    fun provideSubscribeIdUseCase(sensorsRepository: SensorsRepository): SubscribeIdUseCase {
        return SubscribeIdUseCase(
            sensorsRepository
        )
    }

    @Provides
    fun provideUnSubscribeIdUseCase(sensorsRepository: SensorsRepository): UnSubscribeIdUseCase {
        return UnSubscribeIdUseCase(
            sensorsRepository
        )
    }


    @Provides
    fun provideGetSensorO2DataUseCase(sensorsRepository: SensorsRepository): GetSensorO2DataUseCase {
        return GetSensorO2DataUseCase(
            sensorsRepository
        )
    }

    @Provides
    fun provideGetSensorBloodDataUseCase(sensorsRepository: SensorsRepository): GetSensorBloodDataUseCase {
        return GetSensorBloodDataUseCase(
            sensorsRepository
        )
    }

    @Provides
    fun provideGetSensorHeartDataUseCase(sensorsRepository: SensorsRepository): GetSensorHeartDataUseCase {
        return GetSensorHeartDataUseCase(
            sensorsRepository
        )
    }

    @Provides
    fun provideGetSensorTempDataUseCase(sensorsRepository: SensorsRepository): GetSensorTempDataUseCase {
        return GetSensorTempDataUseCase(
            sensorsRepository
        )
    }

    @Provides
    fun provideGetDeviceIdByPatientId(patientsRepository: PatientsRepository): GetDeviceIdByPatientId {
        return GetDeviceIdByPatientId(
            patientsRepository
        )
    }

    @Provides
    fun provideGetDeviceIdByInternmentId(internmentsRepository: InternmentsRepository): GetDeviceIdByInternmentId {
        return GetDeviceIdByInternmentId(
            internmentsRepository
        )
    }

    @Provides
    fun provideSensorsViewModelFactory(
        subscribeIdUseCase: SubscribeIdUseCase,
        unSubscribeIdUseCase: UnSubscribeIdUseCase,
        getSensorO2DataUseCase: GetSensorO2DataUseCase,
        getSensorBloodDataUseCase: GetSensorBloodDataUseCase,
        getSensorHeartDataUseCase: GetSensorHeartDataUseCase,
        getSensorTempDataUseCase: GetSensorTempDataUseCase,
        getDeviceIdByPatientId: GetDeviceIdByPatientId,
        getDeviceIdByInternmentId: GetDeviceIdByInternmentId
    ): SensorsViewModelFactory {
        return SensorsViewModelFactory(
            subscribeIdUseCase,
            unSubscribeIdUseCase,
            getSensorO2DataUseCase,
            getSensorBloodDataUseCase,
            getSensorHeartDataUseCase,
            getSensorTempDataUseCase,
            getDeviceIdByPatientId,
            getDeviceIdByInternmentId
        )
    }
}