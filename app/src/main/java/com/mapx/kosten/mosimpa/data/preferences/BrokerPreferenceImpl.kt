package com.mapx.kosten.mosimpa.data.preferences

import android.content.Context

class BrokerPreferenceImpl(
    context: Context
) : BasePreferencesImpl(context, PREF_BROKER_ID), BrokerPreference {

    override fun getBrokerId(): Long {
        return getLong(KEY_BROKER_ID) ?: -1
    }

    override fun setBrokerId(id: Long) {
        putLong(KEY_BROKER_ID, id)
    }

    override fun getBrokerName(): String {
        return getString(KEY_BROKER_NAME) ?: ""
    }

    override fun setBrokerName(ip: String) {
        putString(KEY_BROKER_NAME, ip)
    }

    override fun getBrokerIP(): String {
        return getString(KEY_BROKER_IP) ?: ""
    }

    override fun setBrokerIP(ip: String) {
        putString(KEY_BROKER_IP, ip)
    }

    companion object{
        private const val KEY_BROKER_ID = "key_broker_id"
        private const val KEY_BROKER_NAME = "key_broker_name"
        private const val KEY_BROKER_IP = "key_broker_ip"
        private const val PREF_BROKER_ID = "pref_broker_id"
    }
}