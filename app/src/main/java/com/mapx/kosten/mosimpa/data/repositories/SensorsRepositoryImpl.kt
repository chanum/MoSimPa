package com.mapx.kosten.mosimpa.data.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.mapx.kosten.mosimpa.data.Utils.Companion.SENSOR_HR
import com.mapx.kosten.mosimpa.data.Utils.Companion.SENSOR_SPO2
import com.mapx.kosten.mosimpa.data.Utils.Companion.SENSOR_TEMP
import com.mapx.kosten.mosimpa.data.client.MqttClient
import com.mapx.kosten.mosimpa.data.db.MosimpaDatabase
import com.mapx.kosten.mosimpa.data.db.dao.SensorSpo2Dao
import com.mapx.kosten.mosimpa.data.db.dao.SensorsDao
import com.mapx.kosten.mosimpa.data.entities.SensorDB
import com.mapx.kosten.mosimpa.data.entities.SensorSpo2DB
import com.mapx.kosten.mosimpa.data.mappers.SensorDataToEntityMapper
import com.mapx.kosten.mosimpa.data.mappers.SensorMqttToEntityMapper
import com.mapx.kosten.mosimpa.data.mappers.SensorSpo2DataToEntityMapper
import com.mapx.kosten.mosimpa.domain.data.SensorsRepository
import com.mapx.kosten.mosimpa.domain.entites.SensorEntity
import com.mapx.kosten.mosimpa.domain.entites.SensorSpo2Entity
import io.reactivex.Observable
import io.reactivex.subjects.ReplaySubject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.json.JSONObject

class SensorsRepositoryImpl(
    context: Context,
    database: MosimpaDatabase
): SensorsRepository {

    private val sensorDao: SensorsDao = database.sensorsDao()
    private val sensorSpo2Dao: SensorSpo2Dao = database.sensorSpo2Dao()

    private val mapperDBtoEntity = SensorDataToEntityMapper()
    private val mapperSpo2DBtoEntity = SensorSpo2DataToEntityMapper()
    private val mapperMqttToDd = SensorMqttToEntityMapper()

    private val mqttClient = MqttClient(context)
    private val sensorHR = ReplaySubject.create<SensorEntity>()

    private var currentId: Long = -1
    private var sensorSpo2Count: Long = 0
    private var sensorBloopPCount: Long = 0
    private var sensorHeartRCount: Long = 0

    val sensorX: LiveData<SensorSpo2Entity> = Transformations.map(
        sensorSpo2Dao.getData()
    ) { it?.let { mapperSpo2DBtoEntity.mapFrom(it) } }

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
        // return data only for the current id
        val st = String.format("%02x", currentId)
        val currentTopic = "reads/${st}"
        if (currentTopic.equals(topic)) {
            parseAndSaveSensor(message.toString())
        }
    }

    // TODO use Generic to identify sensors class
    private fun parseAndSaveSensor(msg: String) {
        val jsonObject = JSONObject(msg)
        if (jsonObject.has(SENSOR_SPO2)) {
            val sensorDb = mapperMqttToDd.mapFromSpo2(msg)
            // TODO mapper
            val sensorSpo2DB = SensorSpo2DB(
                id = 0,
                deviceId = -1,
                time = sensorDb.spo2[0].time,
                spo2 = sensorDb.spo2[0].spO2,
                r = sensorDb.spo2[0].r
            )
            saveSpo2Sensor(sensorSpo2DB)
        } else if (jsonObject.has(SENSOR_HR)) {
            val sensorDb = mapperMqttToDd.mapFromHeartR(msg)
            // saveSensor((SensorDB(id=0, name="spo2", value = sensorCount.toFloat())))
        } else if (jsonObject.has(SENSOR_TEMP)) {
            val sensorDb = mapperMqttToDd.mapFromBloodP(msg)
            // saveSensor((SensorDB(id=0, name="spo2", value = sensorCount.toFloat())))
        }
    }

    private fun saveSpo2Sensor(sensor: SensorSpo2DB) {
        saveSensorSpo2DB(sensor)
        sensorSpo2Count++
        if (sensorSpo2Count >= SENSOR_MAX_COUNT) {
            // clear all sensors in DB
            clearSensorsDB()
            sensorSpo2Count = 0
        }
    }

    private fun saveSensorSpo2DB(sensor: SensorSpo2DB) {
        CoroutineScope(Dispatchers.Default).launch {
            sensorSpo2Dao.insert(sensor)
        }
    }

    private fun clearSensorsDB() {
        CoroutineScope(Dispatchers.Default).launch {
            sensorDao.clear()
        }
    }

    override fun getSpo2Data(): LiveData<SensorSpo2Entity> {
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