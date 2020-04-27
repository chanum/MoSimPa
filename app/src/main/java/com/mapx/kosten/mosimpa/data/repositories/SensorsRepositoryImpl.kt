package com.mapx.kosten.mosimpa.data.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.mapx.kosten.mosimpa.data.client.MqttClient
import com.mapx.kosten.mosimpa.data.db.MosimpaDatabase
import com.mapx.kosten.mosimpa.data.db.dao.*
import com.mapx.kosten.mosimpa.data.entities.*
import com.mapx.kosten.mosimpa.data.mappers.*
import com.mapx.kosten.mosimpa.data.preferences.BrokerIpPreferenceImpl
import com.mapx.kosten.mosimpa.domain.common.Constants.Companion.DEFAULT_MAC_ADDRESS
import com.mapx.kosten.mosimpa.domain.common.Constants.Companion.SENSOR_BLOOD_ID
import com.mapx.kosten.mosimpa.domain.common.Constants.Companion.SENSOR_HEART_ID
import com.mapx.kosten.mosimpa.domain.common.Constants.Companion.SENSOR_O2_ID
import com.mapx.kosten.mosimpa.domain.common.Constants.Companion.SENSOR_TEMPERATURE_ID
import com.mapx.kosten.mosimpa.domain.common.Constants.Companion.SERVER_URI_PREFIX
import com.mapx.kosten.mosimpa.domain.common.Utils.Companion.scaleSensorValueByID
import com.mapx.kosten.mosimpa.domain.data.SensorsRepository
import com.mapx.kosten.mosimpa.domain.entites.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.json.JSONObject

class SensorsRepositoryImpl(
    private val context: Context,
    database: MosimpaDatabase
): SensorsRepository {

    private val sensorO2Dao: SensorO2Dao = database.sensorO2Dao()
    private val sensorBloodDao: SensorBloodDao = database.sensorBloodDao()
    private val sensorHeartDao: SensorHeartDao = database.sensorHeartDao()
    private val sensorTempDao: SensorTempDao = database.sensorTempDao()

    private val internmentsDao: InternmentsDao = database.internmentDao()

    private val mapperO2DBtoEntity = SensorO2DataToEntityMapper()
    private val mapperHeartDBtoEntity = SensorHeartDataToEntityMapper()
    private val mapperBloodDBtoEntity = SensorBloodDataToEntityMapper()
    private val mapperTempDBtoEntity = SensorTempDataToEntityMapper()

    private val mapperMqttToDd = SensorMqttToEntityMapper()
    private val mapperInternmentsMqttToDd = InternmentsMqttToEntityRspMapper()

    private lateinit var mqttClient: MqttClient

    private var currentPatient = InternmentEntity()
    private var sensorO2Count: Long = 0
    private var sensorBloodCount: Long = 0
    private var sensorHeartCount: Long = 0
    private var sensorTempCount: Long = 0

    private var macAddress: String = DEFAULT_MAC_ADDRESS

    private var devices = mutableListOf<String>()
    val deviceList: MutableLiveData<String> = MutableLiveData()

    private var updatePatientsFlag = false
    private var updatePatientsFlag2 = false

    override fun observeDevices(): LiveData<String> {
        return deviceList
    }

    /* ---------------------------------------------------------------------------------------*/
//    override fun getO2Data(patient: PatientEntity) = liveData<SensorO2Entity?> {
//        emitSource(
//            Transformations.map(sensorO2Dao.getData()) {
//                it?.let { mapperO2DBtoEntity.mapFrom(it) }
//            }
//        )
//    }

    override fun getO2Data(internment: InternmentEntity): LiveData<SensorO2Entity> {
        currentPatient = internment
        return Transformations.map(sensorO2Dao.getData()) {
            it?.let { mapperO2DBtoEntity.mapFrom(it) }
        }
    }

    override fun getBloodData(internment: InternmentEntity): LiveData<SensorBloodEntity> {
        currentPatient = internment
        return Transformations.map(sensorBloodDao.getData()) {
            it?.let { mapperBloodDBtoEntity.mapFrom(it) }
        }
    }

    override fun getHeartData(internment: InternmentEntity): LiveData<SensorHeartEntity> {
        currentPatient = internment
        return Transformations.map(sensorHeartDao.getData()) {
            it?.let { mapperHeartDBtoEntity.mapFrom(it) }
        }
    }

    override fun getTempData(internment: InternmentEntity): LiveData<SensorTempEntity> {
        currentPatient = internment
        return Transformations.map(sensorTempDao.getData()) {
            it?.let { mapperTempDBtoEntity.mapFrom(it) }
        }
    }

    /* ---------------------------------------------------------------------------------------*/
    override suspend fun connectMqtt(mac: String) {
        macAddress = mac
        withContext(Dispatchers.IO) {
            val url = getBrokerIp()
            if (::mqttClient.isInitialized) {
                if (isANewUrl(url)) {
                    mqttClient.close()
                    connectAndSubscribe(url)
                }
            } else {
                connectAndSubscribe(url)
            }
        }
    }

    private suspend fun connectAndSubscribe(url: String) {
        mqttClient = MqttClient(context, url)
        subscribeToAll()
    }

    private fun isANewUrl(url: String): Boolean {
        val urlClient = mqttClient.client.serverURI
        return !urlClient.equals(SERVER_URI_PREFIX + url)
    }

    /* ---------------------------------------------------------------------------------------*/
    override suspend fun subscribeToAll() {
        // withContext(Dispatchers.IO) {
            val topic = arrayOf("monitor/$macAddress", "reads/#")
            mqttClient.connect(topic, ::subscribeToAllRsp)
        //}
    }

    private fun subscribeToAllRsp(topic: String, message: MqttMessage) {
        if (topic.startsWith("reads/")) {
            // TODO find a better way
            updatePatients()
            // trim topic
            val id = topic.substring(6)
            // check if exist in the
            if (id !in devices) {
                devices.add(id)
                deviceList.value = id
            }

            val currentTopic = "reads/${currentPatient.deviceId}"
            if (currentTopic.equals(topic)) {
                parseAndSaveSensor(message.toString())
            }
        } else if (topic.startsWith("monitor/$macAddress")) {
            parseAndSavePatients(message.toString())
        }
    }

    private fun updatePatients() {
        // only once
        if (!updatePatientsFlag) {
            val topic = "datakeeper/query"
            val msg = "{\"mac\":\"$macAddress\",\"command\":\"internments\",\"id\":\"123AABB\"}"
            mqttClient.publishMessage(topic, msg)
            updatePatientsFlag = true
        }
    }


    /* ---------------------------------------------------------------------------------------*/
    override fun subscribeId(internment: InternmentEntity) {
        // withContext(Dispatchers.IO) {
            currentPatient.id = internment.id
            currentPatient.deviceId = internment.deviceId
            // val topic = arrayOf("reads/${currentPatient.deviceId}")
            // val topic = "reads/${currentPatient.deviceId}"
            // mqttClient.connect(topic, ::subscribeIdRsp)
            // mqttClient.subscribeTopic(topic)
        // }
    }

    private fun subscribeIdRsp(topic: String, message: MqttMessage) {
        // return data only for the current id
        val currentTopic = "reads/${currentPatient.deviceId}"
        if (currentTopic.equals(topic)) {
            parseAndSaveSensor(message.toString())
        }
    }

    /* ---------------------------------------------------------------------------------------*/
    override fun unSubscribeId(internment: InternmentEntity) {
        // withContext(Dispatchers.IO) {
            val st = String.format("%02x", internment.deviceId)
            val topic = "reads/${st}"
            mqttClient.unSubscribe(topic)
        // }
    }

    /* ---------------------------------------------------------------------------------------*/

    private fun parseAndSavePatients(msg: String) {
        val internmentList = mapperInternmentsMqttToDd.mapFrom(msg)
        internmentList.let {
            // TODO mapper to DB
            it.internments.forEach {
                // add internment to DB
                val internment = InternmentDB(
                    id = it.internment_id,
                    device = it.device,
                    patient = it.patient,
                    location = it.location,
                    alarms = it.alarms
                )
                saveIntermentToDb(internment)
            }
        }
    }


    private fun saveIntermentToDb(internment: InternmentDB) {
        CoroutineScope(Dispatchers.IO).launch {
            internmentsDao.insert(internment)
        }
    }

    /* ---------------------------------------------------------------------------------------*/
    // TODO use Generic to identify sensors class
    // and generic to save sensors
    private fun parseAndSaveSensor(msg: String) {
        val jsonObject = JSONObject(msg)
        if (jsonObject.has(SENSOR_O2_JSON_KEY)) {
            val sensorList = mapperMqttToDd.mapFromO2(msg)
            // TODO mapper
            val sensorO2DB = SensorO2DB(
                id = 0,
                patient_id = currentPatient.id,
                time = sensorList.spo2[0].time,
                spo2 = scaleSensorValueByID(SENSOR_O2_ID, sensorList.spo2[0].spO2),
                r = sensorList.spo2[0].r
            )
            saveO2Sensor(sensorO2DB)
        } else if (jsonObject.has(SENSOR_BLOOD_JSON_KEY)) {
            val sensorList = mapperMqttToDd.mapFromBlood(msg)
            val sensorBloodDB = SensorBloodDB(
                id = 0,
                patient_id = currentPatient.id,
                time = sensorList.bloodP[0].time,
                sys = scaleSensorValueByID(SENSOR_BLOOD_ID, sensorList.bloodP[0].sys.toFloat()).toInt(),
                dia = scaleSensorValueByID(SENSOR_BLOOD_ID, sensorList.bloodP[0].dia.toFloat()).toInt()
            )
            saveBloodSensor(sensorBloodDB)
        } else if (jsonObject.has(SENSOR_HEART_JSON_KEY)) {
            val sensorList = mapperMqttToDd.mapFromHeart(msg)
            val sensorHeartDB = SensorHeartDB(
                id = 0,
                patient_id = currentPatient.id,
                time = sensorList.heartR[0].time,
                heartR = scaleSensorValueByID(SENSOR_HEART_ID, sensorList.heartR[0].heartR.toFloat()).toInt(),
                HR_AR = sensorList.heartR[0].HR_AR
            )
            saveHeartSensor(sensorHeartDB)
        } else if (jsonObject.has(SENSOR_TEMP_JSON_KEY)) {
            val sensorList = mapperMqttToDd.mapFromTemp(msg)
            val sensorTempDB = SensorTempDB(
                id = 0,
                patient_id = currentPatient.id,
                time = sensorList.bodyT[0].time,
                temp = scaleSensorValueByID(SENSOR_TEMPERATURE_ID, sensorList.bodyT[0].temp)
            )
            saveTempSensor(sensorTempDB)
        }
    }

    /* ---------------------------------------------------------------------------------------*/
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
        CoroutineScope(Dispatchers.IO).launch {
            sensorO2Dao.insert(sensor)
        }
    }

    private fun clearO2SensorsDB() {
        CoroutineScope(Dispatchers.IO).launch {
            sensorO2Dao.clear()
        }
    }

    /* ---------------------------------------------------------------------------------------*/
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
        CoroutineScope(Dispatchers.IO).launch {
            sensorBloodDao.insert(sensor)
        }
    }

    private fun clearBloodSensorsDB() {
        CoroutineScope(Dispatchers.IO).launch {
            sensorBloodDao.clear()
        }
    }

    /* ---------------------------------------------------------------------------------------*/
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
        CoroutineScope(Dispatchers.IO).launch {
            sensorHeartDao.insert(sensor)
        }
    }

    private fun clearHeartSensorsDB() {
        CoroutineScope(Dispatchers.IO).launch {
            sensorHeartDao.clear()
        }
    }

    /* ---------------------------------------------------------------------------------------*/
    private fun saveTempSensor(sensor: SensorTempDB) {
        saveSensorTempDB(sensor)
        sensorTempCount++
        if (sensorTempCount >= SENSOR_MAX_COUNT) {
            // clear all sensors in DB
            clearTempSensorsDB()
            sensorTempCount = 0
        }
    }

    private fun saveSensorTempDB(sensor: SensorTempDB) {
        CoroutineScope(Dispatchers.IO).launch {
            sensorTempDao.insert(sensor)
        }
    }

    private fun clearTempSensorsDB() {
        CoroutineScope(Dispatchers.IO).launch {
            sensorTempDao.clear()
        }
    }

    /* ---------------------------------------------------------------------------------------*/
    // TODO settingsRepository?
    private fun getBrokerIp(): String {
        return BrokerIpPreferenceImpl(context).getBrokerIP()
    }

    /* ---------------------------------------------------------------------------------------*/
    companion object {
        const val SENSOR_MAX_COUNT = 100L
    }
}