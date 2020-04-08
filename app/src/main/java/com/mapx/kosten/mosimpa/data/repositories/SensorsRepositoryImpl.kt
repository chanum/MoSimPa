package com.mapx.kosten.mosimpa.data.repositories

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.mapx.kosten.mosimpa.data.client.MqttClient
import com.mapx.kosten.mosimpa.data.db.MosimpaDatabase
import com.mapx.kosten.mosimpa.data.db.dao.SensorsDao
import com.mapx.kosten.mosimpa.data.entities.SensorDB
import com.mapx.kosten.mosimpa.data.mappers.SensorDataToEntityMapper
import com.mapx.kosten.mosimpa.domain.data.SensorsRepository
import com.mapx.kosten.mosimpa.domain.entites.SensorEntity
import io.reactivex.Observable
import io.reactivex.subjects.ReplaySubject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.eclipse.paho.client.mqttv3.MqttMessage

class SensorsRepositoryImpl(
    context: Context,
    database: MosimpaDatabase
): SensorsRepository {

    private val dao: SensorsDao = database.sensorsDao()
    private val mapperDBtoEntity = SensorDataToEntityMapper()

    private val mqttClient = MqttClient(context)
    private val sensorHR = ReplaySubject.create<SensorEntity>()
    private var currentId: Long = -1
    private var sensorCount: Long = 0

    val sensorX: LiveData<SensorEntity> = Transformations.map(dao.getSpo2Data()) { mapperDBtoEntity.mapFrom(it)}



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
            // save the only 1000 latest values
            saveSensor((SensorDB(id=0, name="spo2", value = sensorCount.toFloat())))
        }
    }

    private fun saveSensor(sensor: SensorDB) {
        saveSensorDB(sensor)
        sensorCount++
        if (sensorCount >= SENSOR_MAX_COUNT) {
            // clear all sensors in DB
            clearSensorsDB()
            sensorCount = 0
        }
    }

    private fun saveSensorDB(sensor: SensorDB) {
        CoroutineScope(Dispatchers.Default).launch {
            dao.insert(sensor)
        }
    }

    private fun clearSensorsDB() {
        CoroutineScope(Dispatchers.Default).launch {
            dao.clear()
        }
    }

    override fun getSpo2Data(): LiveData<SensorEntity> {
        val hardId = 0xb827eb8b862d
        currentId = hardId
        return sensorX
    }

    /*
    suspend fun refreshSensorData() {
        withContext(Dispatchers.IO) {
            val playlist = Network.devbytes.getPlaylist().await()
            database.videoDao.insertAll(*playlist.asDatabaseModel())
        }
    }
    */
    companion object {
        const val TAG = "SensorsRepositoryImpl"
        const val SENSOR_MAX_COUNT = 100L
    }
}