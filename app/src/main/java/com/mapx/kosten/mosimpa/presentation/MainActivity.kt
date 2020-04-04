package com.mapx.kosten.mosimpa.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapx.kosten.mosimpa.R
import com.mapx.kosten.mosimpa.data.client.MqttClient

class MainActivity : AppCompatActivity() {

    private val topics = arrayOf("#")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var mqttClient = MqttClient(this)
        mqttClient.connect(topics)
    }

}
