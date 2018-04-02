package ruan.com.Net.MqttManager;

import android.content.Context;

import org.eclipse.paho.client.mqttv3.MqttClient;

import ruan.com.Net.BaseNetCallback;
import ruan.com.Net.MqttManager.Interface.MqttCallback;

/**
 * 调用mqtt 使用的实体类
 * Created by Administrator on 2018/4/2.
 */

public class MqttRequest extends BaseRequest {

    private MqttClient mqttClient;
    private MqttCallback.Response response;

    public MqttRequest(Context context, BaseNetCallback netCallback , MqttCallback.Response response) {
        super(context, netCallback);
        this.response = response;
    }


    public MqttRequest(Context context , MqttCallback.Response response) {
        this(context, null , response);
    }

    /**
     * 请求链接mqtt服务器
     * @param topicFilter
     */
    public void init(String topicFilter) {
        mqttClient = init(topicFilter, new ObservableCallback() {
            @Override
            public void onSuccess(String topic, String message) {
                response.onSuccess(topic , message);
            }

            @Override
            public void onError(String topic, Throwable throwable) {
                response.onError(topic , throwable);
            }
        });
        if (mqttClient == null)
            return;
    }

    /**
     * 发送数据到服务器
     * @param topicFilter       订阅标识
     * @param message           发送数据
     */
    public void send(String topicFilter, String message){
        request(mqttClient, topicFilter, message, new ObservableCallback() {
            @Override
            public void onSuccess(String topic, String message) {
                response.onSuccess(topic , message);
            }

            @Override
            public void onError(String topic, Throwable throwable) {
                response.onError(topic , throwable);
            }
        });
    }

}
