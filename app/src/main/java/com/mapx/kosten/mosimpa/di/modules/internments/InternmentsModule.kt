package com.mapx.kosten.mosimpa.di.modules.internments

import com.mapx.kosten.mosimpa.domain.data.InternmentsRepository
import com.mapx.kosten.mosimpa.domain.data.SensorsRepository
import com.mapx.kosten.mosimpa.domain.interactors.internments.GetInternmentsUseCase
import com.mapx.kosten.mosimpa.domain.interactors.sensor.*
import com.mapx.kosten.mosimpa.presentation.fragments.internments.InternmentsViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class InternmentsModule {

    @Provides
    fun provideConnectClientMqttUseCase(sensorsRepository: SensorsRepository): ConnectClientMqttUseCase {
        return ConnectClientMqttUseCase(
            sensorsRepository
        )
    }

    @Provides
    fun provideGetInternmentsUseCase(internmentsRepository: InternmentsRepository): GetInternmentsUseCase {
        return GetInternmentsUseCase(
            internmentsRepository
        )
    }

    @Provides
    fun provideUpdateInternmentsUseCase(sensorsRepository: SensorsRepository): UpdateInternmentsUseCase {
        return UpdateInternmentsUseCase(
            sensorsRepository
        )
    }

    @Provides
    fun provideSubscribeIdUseCase(sensorsRepository: SensorsRepository): SubscribeIdUseCase {
        return SubscribeIdUseCase(
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
    fun provideInternmentsViewModelFactory(
        connectClientMqttUseCase: ConnectClientMqttUseCase,
        getInternmentsUseCase: GetInternmentsUseCase,
        subscribeIdUseCase: SubscribeIdUseCase,
        updateInternmentsUseCase: UpdateInternmentsUseCase,
        getSensorO2DataUseCase: GetSensorO2DataUseCase,
        getSensorBloodDataUseCase: GetSensorBloodDataUseCase,
        getSensorHeartDataUseCase: GetSensorHeartDataUseCase,
        getSensorTempDataUseCase: GetSensorTempDataUseCase
    ): InternmentsViewModelFactory {
        return InternmentsViewModelFactory(
            connectClientMqttUseCase,
            getInternmentsUseCase,
            updateInternmentsUseCase,
            subscribeIdUseCase,
            getSensorO2DataUseCase,
            getSensorBloodDataUseCase,
            getSensorHeartDataUseCase,
            getSensorTempDataUseCase
        )
    }
}