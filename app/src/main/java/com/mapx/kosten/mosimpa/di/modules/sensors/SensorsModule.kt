package com.mapx.kosten.mosimpa.di.modules.sensors

import com.mapx.kosten.mosimpa.domain.data.SensorsRepository
import com.mapx.kosten.mosimpa.domain.interactors.sensor.GetSensorDataUseCase
import com.mapx.kosten.mosimpa.domain.interactors.sensor.GetSensorSpo2DataUseCase
import com.mapx.kosten.mosimpa.domain.interactors.sensor.SubscribeIdUseCase
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
    fun provideGetSensorSpo2DataUseCase(sensorsRepository: SensorsRepository): GetSensorSpo2DataUseCase {
        return GetSensorSpo2DataUseCase(
            sensorsRepository
        )
    }

    @Provides
    fun provideSensorsViewModelFactory(
        getSensorDataUseCase: GetSensorDataUseCase,
        subscribeIdUseCase: SubscribeIdUseCase,
        getSensorSpo2DataUseCase: GetSensorSpo2DataUseCase
    ): SensorsViewModelFactory {
        return SensorsViewModelFactory(
            getSensorDataUseCase,
            subscribeIdUseCase,
            getSensorSpo2DataUseCase
        )
    }
}