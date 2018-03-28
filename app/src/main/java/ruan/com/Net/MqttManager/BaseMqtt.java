package ruan.com.Net.MqttManager;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * Created by Administrator on 2018/3/27.
 */

public class BaseMqtt extends Service{

    private MqttClient mqttClient;


    public BaseMqtt(){
        try {
            mqttClient = new MqttClient("tcp://broker.mqttdashboard.com:1883" , "android-client" , new MemoryPersistence());

            mqttClient.setCallback(new PushCallback(this));
            mqttClient.connect();

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
