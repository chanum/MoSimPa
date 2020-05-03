package com.mapx.kosten.mosimpa.data.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.mapx.kosten.mosimpa.data.services.client.MqttClient
import com.mapx.kosten.mosimpa.data.db.MosimpaDatabase
import com.mapx.kosten.mosimpa.data.db.dao.*
import com.mapx.kosten.mosimpa.data.entities.*
import com.mapx.kosten.mosimpa.data.mappers.*
import com.mapx.kosten.mosimpa.data.preferences.BrokerIpPreferenceImpl
import com.mapx.kosten.mosimpa.domain.common.Constants.Companion.DEFAULT_MAC_ADDRESS
import com.mapx.kosten.mosimpa.domain.common.Constants.Companion.MQTT_CONNECTION_OK
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
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

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

    private var mqttClient: MqttClient? = null

    private var internments: MutableList<InternmentDB> = mutableListOf()

    private var sensorO2Count: Long = 0
    private var sensorBloodCount: Long = 0
    private var sensorHeartCount: Long = 0
    private var sensorTempCount: Long = 0

    private var macAddress: String = DEFAULT_MAC_ADDRESS

    /* ---------------------------------------------------------------------------------------*/
    private fun getMqttClient(url: String, createNewInstance: Boolean = false): MqttClient {
        if (mqttClient == null || createNewInstance) {
            mqttClient = MqttClient(context, url)
        }
        return mqttClient!!
    }

    /* ---------------------------------------------------------------------------------------*/
    override fun getO2Data(): LiveData<SensorO2Entity> {
        return Transformations.map(sensorO2Dao.getData()) {
            it?.let { mapperO2DBtoEntity.mapFrom(it) }
        }
    }

    override fun getBloodData(): LiveData<SensorBloodEntity> {
        return Transformations.map(sensorBloodDao.getData()) {
            it?.let { mapperBloodDBtoEntity.mapFrom(it) }
        }
    }

    override fun getHeartData(): LiveData<SensorHeartEntity> {
        return Transformations.map(sensorHeartDao.getData()) {
            it?.let { mapperHeartDBtoEntity.mapFrom(it) }
        }
    }

    override fun getTempData(): LiveData<SensorTempEntity> {
        return Transformations.map(sensorTempDao.getData()) {
            it?.let { mapperTempDBtoEntity.mapFrom(it) }
        }
    }

    /* ---------------------------------------------------------------------------------------*/
    override suspend fun connectMqtt(mac: String): String {
        macAddress = mac
        return withContext(Dispatchers.IO) {
            var status = ""
            val url = getBrokerIp()
            if (mqttClient != null) {
                if (isANewUrl(url)) {
                    mqttClient?.let {it.close()}
                    getMqttClient(url, true)
                }
            } else {
                getMqttClient(url)
            }
            status = subscribeToAll()
            status
        }
    }

    private fun isANewUrl(url: String): Boolean {
        var result = false
        mqttClient?.let {
            val urlClient = it.client.serverURI
            result = !urlClient.equals(SERVER_URI_PREFIX + url)
        }
        return result
    }

    /* ---------------------------------------------------------------------------------------*/

    override suspend fun subscribeToAll(): String {
        val topics = arrayOf("monitor/$macAddress", "reads/#")
        return suspendCoroutine { msg ->
            mqttClient?.connect(topics) { topic: String, message: MqttMessage?  ->
                if(topic.equals(MQTT_CONNECTION_OK)) {
                    msg.resume(topic)
                } else {
                   subscribeToAllRsp(topic, message)
                }
            }
        }
    }

    /* ---------------------------------------------------------------------------------------*/

    private fun subscribeToAllRsp(topic: String, message: MqttMessage?) {
        if (topic.startsWith("reads/")) {
            internments.forEach {
                val deviceId = "reads/${it.device}"
                if (deviceId.equals(topic)) {
                    parseAndSaveSensor(message.toString(), it.id)
                }
            }
        } else if (topic.startsWith("monitor/$macAddress")) {
            parseAndSavePatients(message.toString())
        }
    }

    override fun updateInternments() {
        // TODO id
        if (mqttClient != null) {
            val topic = "datakeeper/query"
            val msg = "{\"mac\":\"$macAddress\",\"command\":\"internments\",\"id\":\"123AABB\"}"
            mqttClient?.publishMessage(topic, msg)
        }
    }

    /* ---------------------------------------------------------------------------------------*/

    override fun subscribeId(internment: InternmentEntity) {
        // TODO
    }

    /* ---------------------------------------------------------------------------------------*/
    override fun unSubscribeId(internment: InternmentEntity) {
        val st = String.format("%02x", internment.deviceId)
        val topic = "reads/${st}"
        mqttClient?.unSubscribe(topic)
    }

    /* ---------------------------------------------------------------------------------------*/

    private fun parseAndSavePatients(msg: String) {
        val internmentList = mapperInternmentsMqttToDd.mapFrom(msg)
        internmentList.let {
            // TODO mapper to DB
            it.internments.forEach {
                // add internment to DB
                val alarms = AlarmsEntity (
                    spo2_lt = scaleSensorValueByID(SENSOR_O2_ID, it.alarms.spo2_lt),
                    hr_lt = scaleSensorValueByID(SENSOR_O2_ID, it.alarms.hr_lt),
                    hr_gt = scaleSensorValueByID(SENSOR_O2_ID, it.alarms.hr_gt),
                    bt_gt= scaleSensorValueByID(SENSOR_O2_ID, it.alarms.bt_gt),
                    bp_sys_lt = scaleSensorValueByID(SENSOR_O2_ID, it.alarms.bp_sys_lt),
                    bp_sys_gt= scaleSensorValueByID(SENSOR_O2_ID, it.alarms.bp_sys_gt)
                )
                val internment = InternmentDB(
                    id = it.internment_id,
                    device = it.device,
                    patient = it.patient,
                    location = it.location,
                    alarms = alarms
                )
                saveIntermentToDb(internment)
                internments.add(internment)
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
    private fun parseAndSaveSensor(msg: String, intermentId: Long) {
        val jsonObject = JSONObject(msg)
        if (jsonObject.has(SENSOR_O2_JSON_KEY)) {
            val sensorList = mapperMqttToDd.mapFromO2(msg)
            // TODO mapper
            val sensorO2DB = SensorO2DB(
                id = 0,
                internment_id = intermentId,
                time = sensorList.spo2[0].time,
                spo2 = scaleSensorValueByID(SENSOR_O2_ID, sensorList.spo2[0].spO2),
                r = sensorList.spo2[0].r
            )
            saveO2Sensor(sensorO2DB)
        } else if (jsonObject.has(SENSOR_BLOOD_JSON_KEY)) {
            val sensorList = mapperMqttToDd.mapFromBlood(msg)
            val sensorBloodDB = SensorBloodDB(
                id = 0,
                internment_id = intermentId,
                time = sensorList.bloodP[0].time,
                sys = scaleSensorValueByID(SENSOR_BLOOD_ID, sensorList.bloodP[0].sys.toFloat()).toInt(),
                dia = scaleSensorValueByID(SENSOR_BLOOD_ID, sensorList.bloodP[0].dia.toFloat()).toInt()
            )
            saveBloodSensor(sensorBloodDB)
        } else if (jsonObject.has(SENSOR_HEART_JSON_KEY)) {
            val sensorList = mapperMqttToDd.mapFromHeart(msg)
            val sensorHeartDB = SensorHeartDB(
                id = 0,
                internment_id = intermentId,
                time = sensorList.heartR[0].time,
                heartR = scaleSensorValueByID(SENSOR_HEART_ID, sensorList.heartR[0].heartR.toFloat()).toInt(),
                HR_AR = sensorList.heartR[0].HR_AR
            )
            saveHeartSensor(sensorHeartDB)
        } else if (jsonObject.has(SENSOR_TEMP_JSON_KEY)) {
            val sensorList = mapperMqttToDd.mapFromTemp(msg)
            val sensorTempDB = SensorTempDB(
                id = 0,
                internment_id = intermentId,
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