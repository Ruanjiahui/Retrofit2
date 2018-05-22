package ruan.com.Net.MqttManager

import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttException
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence

/**
 * Created by Administrator on 2018/5/4.
 */
class MqttManager {

    companion object {
        var mqttClient: MqttClient? = null

        @Synchronized
        @Throws(MqttException::class)
        fun getInstance(): MqttClient? {
            if (mqttClient == null || !mqttClient!!.isConnected) {
                mqttClient = MqttClient(Settings.MQTT_IP, getClient_ID(), MemoryPersistence())
                return mqttClient
            }
            return mqttClient
        }

        /**
         * 获取每次请求的唯一标识码
         *
         * @return
         */
        private fun getClient_ID(): String {
            return "Android_" + System.currentTimeMillis()
        }
    }
}