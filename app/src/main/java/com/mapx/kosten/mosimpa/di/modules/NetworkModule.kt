package com.mapx.kosten.mosimpa.di.modules

import android.app.Application
import com.mapx.kosten.mosimpa.domain.common.Constants.Companion.SERVER_URI
import dagger.Module
import dagger.Provides
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.MqttClient
import javax.inject.Singleton

@Module
class NetworkModule {

    /*
    @Provides
    @Singleton
    fun provideMqttAndroidClient(application: Application?): MqttAndroidClient? {
        val clientId = MqttClient.generateClientId()
        return MqttAndroidClient(
            application,
            SERVER_URI,
            clientId
        )
    }
  */
}