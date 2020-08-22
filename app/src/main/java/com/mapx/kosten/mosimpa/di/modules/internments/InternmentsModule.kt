package com.mapx.kosten.mosimpa.di.modules.internments

import com.mapx.kosten.mosimpa.domain.data.InternmentsRepository
import com.mapx.kosten.mosimpa.domain.interactors.internments.GetInternmentsUseCase
import com.mapx.kosten.mosimpa.domain.interactors.sensor.*
import com.mapx.kosten.mosimpa.presentation.viewmodels.InternmentsViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class InternmentsModule {

    @Provides
    fun provideConnectClientMqttUseCase(internmentsRepository: InternmentsRepository): ConnectClientMqttUseCase {
        return ConnectClientMqttUseCase(
            internmentsRepository
        )
    }

    @Provides
    fun provideGetInternmentsUseCase(internmentsRepository: InternmentsRepository): GetInternmentsUseCase {
        return GetInternmentsUseCase(
            internmentsRepository
        )
    }

    @Provides
    fun provideUpdateInternmentsUseCase(internmentsRepository: InternmentsRepository): UpdateInternmentsUseCase {
        return UpdateInternmentsUseCase(
            internmentsRepository
        )
    }

    @Provides
    fun provideSubscribeIdUseCase(internmentsRepository: InternmentsRepository): SubscribeIdUseCase {
        return SubscribeIdUseCase(
            internmentsRepository
        )
    }

    @Provides
    fun provideGetSensorO2DataUseCase(internmentsRepository: InternmentsRepository): GetSensorO2DataUseCase {
        return GetSensorO2DataUseCase(
            internmentsRepository
        )
    }

    @Provides
    fun provideGetSensorBloodDataUseCase(internmentsRepository: InternmentsRepository): GetSensorBloodDataUseCase {
        return GetSensorBloodDataUseCase(
            internmentsRepository
        )
    }

    @Provides
    fun provideGetSensorHeartDataUseCase(internmentsRepository: InternmentsRepository): GetSensorHeartDataUseCase {
        return GetSensorHeartDataUseCase(
            internmentsRepository
        )
    }

    @Provides
    fun provideGetSensorTempDataUseCase(internmentsRepository: InternmentsRepository): GetSensorTempDataUseCase {
        return GetSensorTempDataUseCase(
            internmentsRepository
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