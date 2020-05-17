package com.mapx.kosten.mosimpa.data.preferences

interface BrokerPreference {
    fun getBrokerId(): Long
    fun setBrokerId(id: Long)
    fun getBrokerName(): String
    fun setBrokerName(ip: String)
    fun getBrokerIP(): String
    fun setBrokerIP(ip: String)
}