package com.mapx.kosten.mosimpa.di.modules.sensors

import com.mapx.kosten.mosimpa.domain.data.SensorsRepository
import com.mapx.kosten.mosimpa.domain.interactors.sensor.*
import com.mapx.kosten.mosimpa.presentation.common.ASyncTransformer
import com.mapx.kosten.mosimpa.presentation.fragments.sensors.SensorsViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class SensorsModule {

    @Provides
    fun provideGetSensorDataUseCase(sensorsRepository: SensorsRepository): GetSensorDataUseCase {
        return GetSensorDataUseCase(
            ASyncTransformer(),
            sensorsRepository
        )
    }

    @Provides
    fun provideSubscribeIdUseCase(sensorsRepository: SensorsRepository): SubscribeIdUseCase {
        return SubscribeIdUseCase(
            ASyncTransformer(),
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
    fun provideSensorsViewModelFactory(
        getSensorDataUseCase: GetSensorDataUseCase,
        subscribeIdUseCase: SubscribeIdUseCase,
        getSensorO2DataUseCase: GetSensorO2DataUseCase,
        getSensorBloodDataUseCase: GetSensorBloodDataUseCase,
        getSensorHeartDataUseCase: GetSensorHeartDataUseCase
    ): SensorsViewModelFactory {
        return SensorsViewModelFactory(
            getSensorDataUseCase,
            subscribeIdUseCase,
            getSensorO2DataUseCase,
            getSensorBloodDataUseCase,
            getSensorHeartDataUseCase
        )
    }
}