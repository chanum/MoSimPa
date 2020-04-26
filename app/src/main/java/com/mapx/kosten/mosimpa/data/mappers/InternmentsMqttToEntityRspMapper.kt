package com.mapx.kosten.mosimpa.data.mappers

import com.google.gson.Gson
import com.mapx.kosten.mosimpa.data.entities.InternmentsRsp

class InternmentsMqttToEntityRspMapper {
    fun mapFrom(from: String): InternmentsRsp {
        val gson = Gson()
        return gson.fromJson(from, InternmentsRsp::class.java)
    }
}