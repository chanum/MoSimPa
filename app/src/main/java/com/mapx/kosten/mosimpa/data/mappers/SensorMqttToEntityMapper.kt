package com.mapx.kosten.mosimpa.data.mappers

import com.google.gson.Gson
import com.mapx.kosten.mosimpa.data.entities.*

class SensorMqttToEntityMapper {

    fun mapFromSpo2(from: String): SensorSpo2Rsp {
        val gson = Gson()
        return gson.fromJson(from, SensorSpo2Rsp::class.java)
    }

    fun mapFromBloodP(from: String): SensorBloopP {
        val gson = Gson()
        return gson.fromJson(from, SensorBloopP::class.java)
    }

    fun mapFromHeartR(from: String): SensorHeartR {
        val gson = Gson()
        return gson.fromJson(from, SensorHeartR::class.java)
    }
}