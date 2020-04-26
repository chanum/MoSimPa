package com.mapx.kosten.mosimpa.di.modules.internments

import com.mapx.kosten.mosimpa.domain.data.InternmentsRepository
import com.mapx.kosten.mosimpa.domain.data.SensorsRepository
import com.mapx.kosten.mosimpa.domain.interactors.patient.GetInternmentsUseCase
import com.mapx.kosten.mosimpa.domain.interactors.sensor.ConnectClientMqttUseCase
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
    fun provideInternmentsViewModelFactory(
        connectClientMqttUseCase: ConnectClientMqttUseCase,
        getInternmentsUseCase: GetInternmentsUseCase
    ): InternmentsViewModelFactory {
        return InternmentsViewModelFactory(
            connectClientMqttUseCase,
            getInternmentsUseCase
        )
    }
}