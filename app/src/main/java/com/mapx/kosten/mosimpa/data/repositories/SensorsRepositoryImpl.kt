package com.mapx.kosten.mosimpa.data.repositories

import android.content.Context
import com.mapx.kosten.mosimpa.data.client.MqttClient
import com.mapx.kosten.mosimpa.domain.data.SensorsRepository
import com.mapx.kosten.mosimpa.domain.entites.SensorEntity
import io.reactivex.Observable
import org.eclipse.paho.client.mqttv3.MqttMessage

class SensorsRepositoryImpl(
    context: Context
): SensorsRepository {

    private val mqttClient = MqttClient(context)
    private val topic = arrayOf("#")

    override fun subscribeId(id: Long): Observable<Boolean> {
        mqttClient.connect(topic, ::msgRsp)
        return Observable.fromCallable {
            true
        }
    }

    override fun getSensorById(id: Long): Observable<Boolean> {
        // TODO MQQT client move to data di
        // connect and subscribe
        mqttClient.connect(topic, ::msgRsp)
        return Observable.fromCallable {
            true
        }
    }

    private fun msgRsp(topic: String, message: MqttMessage) {
        message.toString()
        // TODO conver string message to observable
    }
}