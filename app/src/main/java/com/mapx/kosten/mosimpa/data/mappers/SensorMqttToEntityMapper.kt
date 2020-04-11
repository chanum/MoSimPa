package com.mapx.kosten.mosimpa.data.mappers

import com.google.gson.Gson
import com.mapx.kosten.mosimpa.data.entities.SensorBloopRsp
import com.mapx.kosten.mosimpa.data.entities.SensorBodyTRsp
import com.mapx.kosten.mosimpa.data.entities.SensorHeartRRsp
import com.mapx.kosten.mosimpa.data.entities.SensorSpo2Rsp

class SensorMqttToEntityMapper {

    fun mapFromO2(from: String): SensorSpo2Rsp {
        val gson = Gson()
        return gson.fromJson(from, SensorSpo2Rsp::class.java)
    }

    fun mapFromBlood(from: String): SensorBloopRsp {
        val gson = Gson()
        return gson.fromJson(from, SensorBloopRsp::class.java)
    }

    fun mapFromHeart(from: String): SensorHeartRRsp {
        val gson = Gson()
        return gson.fromJson(from, SensorHeartRRsp::class.java)
    }

    fun mapFromTemp(from: String): SensorBodyTRsp {
        val gson = Gson()
        return gson.fromJson(from, SensorBodyTRsp::class.java)
    }
}