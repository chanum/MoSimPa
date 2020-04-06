package com.mapx.kosten.mosimpa.data.repositories

import android.content.Context
import android.util.Log
import com.mapx.kosten.mosimpa.data.client.MqttClient
import com.mapx.kosten.mosimpa.domain.data.SensorsRepository
import com.mapx.kosten.mosimpa.domain.entites.SensorEntity
import io.reactivex.Observable
import org.eclipse.paho.client.mqttv3.MqttMessage

class SensorsRepositoryImpl(
    context: Context
): SensorsRepository {

    private val mqttClient = MqttClient(context)
    // private val topic = arrayOf("reads/b827eb8b862d")

    override fun subscribeId(id: Long): Observable<Boolean> {
        val st = String.format("%02x", id)
        val topic = arrayOf("reads/${st}")
        mqttClient.connect(topic, ::msgRsp)
        return Observable.fromCallable {
            true
        }
    }

    override fun getSensorById(id: Long): Observable<Boolean> {
        // TODO MQTT client move to data di
        // connect and subscribe
        // mqttClient.connect(topic, ::msgRsp)
        return Observable.fromCallable {
            true
        }
    }

    fun msgRsp(topic: String, message: MqttMessage) {
        Log.d(TAG, "MQTT Incoming message from $topic: " + message.toString())
        // TODO conver string message to observable
    }

    companion object {
        const val TAG = "SensorsRepositoryImpl"
    }
}