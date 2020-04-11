package com.mapx.kosten.mosimpa.data.client

import android.content.Context
import android.util.Log
import com.mapx.kosten.mosimpa.domain.common.Constants.Companion.SERVER_URI
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import org.eclipse.paho.client.mqttv3.MqttClient

class MqttClient(
    private val context: Context,
    private val serverURI: String
) {

    lateinit var client: MqttAndroidClient

    init {
        initialize(serverURI)
    }

    companion object {
        const val TAG = "MqttClient"
    }

    fun initialize(url: String) {
        val uri = "tcp://" + url
        val clientId = MqttClient.generateClientId()
        client = MqttAndroidClient(context, uri, clientId)
    }

    fun connect(topics: Array<String>? = null,
                messageCallBack: ((topic: String, message: MqttMessage) -> Unit)? = null) {
        try {
            client.connect()
            client.setCallback(object : MqttCallbackExtended {
                override fun connectComplete(reconnect: Boolean, serverURI: String) {
                    topics?.forEach {
                        subscribeTopic(it)
                    }
                    Log.d(TAG, "Connected to: $serverURI")
                }

                override fun connectionLost(cause: Throwable) {
                    Log.d(TAG, "The Connection was lost.")
                }

                @Throws(Exception::class)
                override fun messageArrived(topic: String, message: MqttMessage) {
                    Log.d(TAG, "Incoming message from $topic: " + message.toString())
                    messageCallBack?.invoke(topic, message)
                }

                override fun deliveryComplete(token: IMqttDeliveryToken) {

                }
            })


        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    fun publishMessage(topic: String, msg: String) {

        try {
            val message = MqttMessage()
            message.payload = msg.toByteArray()
            client.publish(topic, message.payload, 0, true)
            Log.d(TAG, "$msg published to $topic")
        } catch (e: MqttException) {
            Log.d(TAG, "Error Publishing to $topic: " + e.message)
            e.printStackTrace()
        }

    }

    fun subscribeTopic(topic: String, qos: Int = 0) {
        client.subscribe(topic, qos).actionCallback = object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken) {
                Log.d(TAG, "Subscribed to $topic")
            }

            override fun onFailure(asyncActionToken: IMqttToken, exception: Throwable) {
                Log.d(TAG, "Failed to subscribe to $topic")
                exception.printStackTrace()
            }
        }
    }

    fun unSubscribe(topic: String) {
        try {
            val unsubToken = client.unsubscribe(topic)
            unsubToken.actionCallback = object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken) {
                    Log.d(TAG, "UnSubscribed to $topic")
                }
                override fun onFailure(asyncActionToken: IMqttToken, exception: Throwable) {
                    Log.d(TAG, "Failed to UnSubscribed to $topic")
                }
            }
        } catch (e: MqttException) {
            // Give your callback on failure here
        }
    }

    fun close() {
        client.apply {
            unregisterResources()
            close()
        }
    }
}
