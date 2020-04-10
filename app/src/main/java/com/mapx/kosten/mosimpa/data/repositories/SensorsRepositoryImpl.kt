package com.mapx.kosten.mosimpa.data.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.mapx.kosten.mosimpa.data.client.MqttClient
import com.mapx.kosten.mosimpa.data.db.MosimpaDatabase
import com.mapx.kosten.mosimpa.data.db.dao.SensorBloodDao
import com.mapx.kosten.mosimpa.data.db.dao.SensorHeartDao
import com.mapx.kosten.mosimpa.data.db.dao.SensorO2Dao
import com.mapx.kosten.mosimpa.data.db.dao.SensorsDao
import com.mapx.kosten.mosimpa.data.entities.*
import com.mapx.kosten.mosimpa.data.mappers.*
import com.mapx.kosten.mosimpa.domain.data.SensorsRepository
import com.mapx.kosten.mosimpa.domain.entites.SensorBloodEntity
import com.mapx.kosten.mosimpa.domain.entites.SensorEntity
import com.mapx.kosten.mosimpa.domain.entites.SensorHeartEntity
import com.mapx.kosten.mosimpa.domain.entites.SensorO2Entity
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
    private val sensorO2Dao: SensorO2Dao = database.sensorO2Dao()
    private val sensorBloodDao: SensorBloodDao = database.sensorBloodDao()
    private val sensorHeartDao: SensorHeartDao = database.sensorHeartDao()

    private val mapperDBtoEntity = SensorDataToEntityMapper()

    private val mapperO2DBtoEntity = SensorO2DataToEntityMapper()
    private val mapperHeartDBtoEntity = SensorHeartDataToEntityMapper()
    private val mapperBloodDBtoEntity = SensorBloodDataToEntityMapper()

    private val mapperMqttToDd = SensorMqttToEntityMapper()

    private val mqttClient = MqttClient(context)
    private val sensorHR = ReplaySubject.create<SensorEntity>()

    private var currentId: Long = -1
    private var sensorO2Count: Long = 0
    private var sensorBloodCount: Long = 0
    private var sensorHeartCount: Long = 0

    val sensorO2: LiveData<SensorO2Entity> = Transformations.map(
        sensorO2Dao.getData()
    ) { it?.let { mapperO2DBtoEntity.mapFrom(it) } }

    val sensorBlood: LiveData<SensorBloodEntity> = Transformations.map(
        sensorBloodDao.getData()
    ) { it?.let { mapperBloodDBtoEntity.mapFrom(it) } }

    val sensorHeart: LiveData<SensorHeartEntity> = Transformations.map(
        sensorHeartDao.getData()
    ) { it?.let { mapperHeartDBtoEntity.mapFrom(it) } }

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
    // and generict to save sensors
    private fun parseAndSaveSensor(msg: String) {
        val jsonObject = JSONObject(msg)
        if (jsonObject.has(SENSOR_O2_JSON_KEY)) {
            val sensorList = mapperMqttToDd.mapFromO2(msg)
            // TODO mapper
            val sensorO2DB = SensorO2DB(
                id = 0,
                deviceId = -1,
                time = sensorList.spo2[0].time,
                spo2 = sensorList.spo2[0].spO2,
                r = sensorList.spo2[0].r
            )
            saveO2Sensor(sensorO2DB)
        } else if (jsonObject.has(SENSOR_BLOOD_JSON_KEY)) {
            val sensorList = mapperMqttToDd.mapFromBlood(msg)
            val sensorBloodDB = SensorBloodDB(
                id = 0,
                deviceId = -1,
                time = sensorList.bloodP[0].time,
                sys = sensorList.bloodP[0].sys,
                dia = sensorList.bloodP[0].dia
            )
            saveBloodSensor(sensorBloodDB)
        } else if (jsonObject.has(SENSOR_HEART_JSON_KEY)) {
            val sensorList = mapperMqttToDd.mapFromHeart(msg)
            val sensorHeartDB = SensorHeartDB(
                id = 0,
                deviceId = -1,
                time = sensorList.heartR[0].time,
                heartR = sensorList.heartR[0].heartR,
                HR_AR = sensorList.heartR[0].HR_AR
            )
            saveHeartSensor(sensorHeartDB)
        }
    }

    private fun saveO2Sensor(sensor: SensorO2DB) {
        saveSensorO2DB(sensor)
        sensorO2Count++
        if (sensorO2Count >= SENSOR_MAX_COUNT) {
            // clear all sensors in DB
            clearO2SensorsDB()
            sensorO2Count = 0
        }
    }

    private fun saveSensorO2DB(sensor: SensorO2DB) {
        CoroutineScope(Dispatchers.Default).launch {
            sensorO2Dao.insert(sensor)
        }
    }

    private fun clearO2SensorsDB() {
        CoroutineScope(Dispatchers.Default).launch {
            sensorO2Dao.clear()
        }
    }

    private fun saveBloodSensor(sensor: SensorBloodDB) {
        saveSensorBloodDB(sensor)
        sensorBloodCount++
        if (sensorBloodCount >= SENSOR_MAX_COUNT) {
            // clear all sensors in DB
            clearBloodSensorsDB()
            sensorBloodCount = 0
        }
    }

    private fun saveSensorBloodDB(sensor: SensorBloodDB) {
        CoroutineScope(Dispatchers.Default).launch {
            sensorBloodDao.insert(sensor)
        }
    }

    private fun clearBloodSensorsDB() {
        CoroutineScope(Dispatchers.Default).launch {
            sensorBloodDao.clear()
        }
    }

    private fun saveHeartSensor(sensor: SensorHeartDB) {
        saveSensorHeartDB(sensor)
        sensorHeartCount++
        if (sensorHeartCount >= SENSOR_MAX_COUNT) {
            // clear all sensors in DB
            clearHeartSensorsDB()
            sensorHeartCount = 0
        }
    }

    private fun saveSensorHeartDB(sensor: SensorHeartDB) {
        CoroutineScope(Dispatchers.Default).launch {
            sensorHeartDao.insert(sensor)
        }
    }

    private fun clearHeartSensorsDB() {
        CoroutineScope(Dispatchers.Default).launch {
            sensorHeartDao.clear()
        }
    }

    override fun getO2Data(): LiveData<SensorO2Entity> {
        val hardId = 0xb827eb8b862d
        currentId = hardId
        return sensorO2
    }

    override fun getBloodData(): LiveData<SensorBloodEntity> {
        val hardId = 0xb827eb8b862d
        currentId = hardId
        return sensorBlood
    }

    override fun getHeartData(): LiveData<SensorHeartEntity> {
        val hardId = 0xb827eb8b862d
        currentId = hardId
        return sensorHeart
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