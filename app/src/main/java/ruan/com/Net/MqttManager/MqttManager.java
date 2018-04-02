package ruan.com.Net.MqttManager;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.nio.charset.Charset;

/**
 * 实例化 mqtt
 * Created by Administrator on 2018/3/27.
 */

public class MqttManager {

    private static MqttClient mqttClient;


    public synchronized static MqttClient getInstance() throws MqttException {
        if (mqttClient == null || !mqttClient.isConnected()) {
            mqttClient = new MqttClient(Settings.MQTT_IP, getClient_ID(), new MemoryPersistence());
            return mqttClient;
        }
        return mqttClient;
    }

    /**
     * 获取每次请求的唯一标识码
     *
     * @return
     */
    private static String getClient_ID() {
        return "Android_" + System.currentTimeMillis();
    }

//    public MqttManager(Context context){
//        try {
//            mqttClient = new MqttClient(Settings.MQTT_IP , "android-client" , new MemoryPersistence());
//
//            mqttClient.setCallback(new PushCallback(context));
//            mqttClient.connect();
//
//            //Subscribe to all subtopics of homeautomation
//            mqttClient.subscribe("test");
//            MqttMessage message = new MqttMessage();
//            String msg = "你好! mqtt server";
//            message.setPayload(msg.getBytes(Charset.forName("utf-8")));
//            mqttClient.publish("test" , message);
//
//        } catch (MqttException e) {
//            e.printStackTrace();
//        }
//    }
}
