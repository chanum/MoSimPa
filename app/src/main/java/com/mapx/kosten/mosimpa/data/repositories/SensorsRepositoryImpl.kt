package com.mapx.kosten.mosimpa.data.repositories

import android.content.Context
import android.util.Log
import com.mapx.kosten.mosimpa.data.client.MqttClient
import com.mapx.kosten.mosimpa.domain.data.SensorsRepository
import com.mapx.kosten.mosimpa.domain.entites.SensorEntity
import io.reactivex.Observable
import io.reactivex.subjects.ReplaySubject
import org.eclipse.paho.client.mqttv3.MqttMessage

class SensorsRepositoryImpl(
    context: Context
): SensorsRepository {

    private val mqttClient = MqttClient(context)
    // private val topic = arrayOf("reads/b827eb8b862d")
    private val sensorHR = ReplaySubject.create<SensorEntity>()
    private var currentId: Long = -1

    override fun subscribeId(id: Long): Observable<Boolean> {
        val st = String.format("%02x", id)
        val topic = arrayOf("reads/${st}")
        mqttClient.connect(topic, ::msgRsp)
        return Observable.fromCallable {
            true
        }
    }

    override fun getSensorById(id: Long): Observable<SensorEntity> {
        currentId = id
        return sensorHR
    }

    fun msgRsp(topic: String, message: MqttMessage) {
        // TODO parse msg
        // return data only for the current id
        val st = String.format("%02x", currentId)
        val currentTopic = "reads/${st}"
        if (currentTopic.equals(topic)) {
            Log.d(TAG, "MQTT Incoming message from $topic: " + message.toString())
            sensorHR.onNext(SensorEntity(value = 32.51F))
        }
    }

    companion object {
        const val TAG = "SensorsRepositoryImpl"
    }
}