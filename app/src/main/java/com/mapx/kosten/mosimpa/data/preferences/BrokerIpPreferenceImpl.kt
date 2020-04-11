package com.mapx.kosten.mosimpa.data.preferences

import android.content.Context

class BrokerIpPreferenceImpl(
    context: Context
) : BasePreferencesImpl(context, PREF_BROKER_ID), BrokerIpPreference {

    override fun getBrokerIP(): String {
        return getString(KEY_BROKER_ID) ?: ""
    }

    override fun setBrokerIP(ip: String) {
        putString(KEY_BROKER_ID, ip)
    }

    companion object{
        private const val KEY_BROKER_ID = "key_broker_id"
        private const val PREF_BROKER_ID = "pref_broker_id"
    }
}