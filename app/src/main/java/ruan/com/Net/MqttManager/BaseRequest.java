package ruan.com.Net.MqttManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.nio.charset.Charset;

import ruan.com.Net.BaseNetCallback;
import ruan.com.Net.NetLog;
import ruan.com.Net.NetWorkUtil;

import static ruan.com.Net.MqttManager.Settings.CONNECT_MSG;
import static ruan.com.Net.MqttManager.Settings.NET_MSG;

/**
 * Created by Administrator on 2018/4/2.
 */

public class BaseRequest {

    private Context context;
    private BaseNetCallback netCallback;
    private ObservableCallback callback;
    private String topicFilter;

    public BaseRequest(Context context, BaseNetCallback netCallback) {
        this.context = context;
        this.netCallback = netCallback;
    }

    protected interface ObservableCallback {

        void onSuccess(String topic, String message);


        void onError(String topic, Throwable throwable);

    }

    /**
     * 初始化链接mqtt的方法
     *
     * @param topicFilter 订阅主题
     * @param callback    回调接口
     * @return
     */
    public MqttClient init(final String topicFilter, final ObservableCallback callback) {
        this.callback = callback;
        this.topicFilter = topicFilter;
        //没有网络直接提示
        if (!NetWorkUtil.isNetWorkAvailable(context)) {

            if (netCallback != null)
                netCallback.noConnect();
            else
                Toast.makeText(context , NET_MSG , Toast.LENGTH_SHORT).show();
            return null;
        }

        try {
            MqttClient mqttClient = MqttManager.getInstance();

            mqttClient.setCallback(new MqttCallback() {
                /**
                 * 链接成功，接收数据失败
                 *
                 * @param cause the reason behind the loss of connection.
                 */
                @Override
                public void connectionLost(Throwable cause) {
                    NetLog.info(cause.toString());

                    Message msg = new Message();
                    msg.obj = cause;
                    handler.sendMessage(msg);
                }

                /**
                 *
                 * 数据到达触发的接口
                 *
                 * @param topic   name of the topic on the message was published to
                 * @param message the actual message.
                 * @throws Exception if a terminal error has occurred, and the client should be
                 *                   shut down.
                 */
                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    NetLog.info(new String(message.getPayload(), Charset.forName("utf-8")));
                    Message msg = new Message();
                    msg.obj = new String(message.getPayload(), Charset.forName("utf-8"));
                    handler.sendMessage(msg);
                }

                /**
                 *
                 *  数据发送之后触发的接口
                 *
                 * @param token the delivery token associated with the message.
                 */
                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    //没有发送成功则重新发送
                    int total = 0;
                    while (!token.isComplete()) {
                        if (total < Settings.count) {
                            //重新发送

                        } else {
                            //重发还是失败则返回错误
                            Message msg = new Message();
                            msg.obj = token.getException();
                            handler.sendMessage(msg);

                            break;
                        }
                    }
                }
            });

            mqttClient.connect();
            //订阅频道
            mqttClient.subscribe(topicFilter);

            return mqttClient;
        } catch (MqttException e) {
            NetLog.info(e.toString());
            Toast.makeText(context , CONNECT_MSG , Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    /**
     * 发送数据
     *
     * @param mqttClient  发送数据的对象
     * @param topicFilter 发布主题
     * @param message     发送信息
     */
    protected void request(MqttClient mqttClient, String topicFilter, String message, ObservableCallback callback) {
        this.callback = callback;
        this.topicFilter = topicFilter;

        //创建发送数据的结构体
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setPayload(message.getBytes(Charset.forName("utf-8")));
        try {
            mqttClient.publish(topicFilter, mqttMessage);
        } catch (MqttException e) {
            NetLog.info(e.toString());
            callback.onError(topicFilter, e);
        }
    }


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){

        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.arg1) {
                //成功
                case 0:
                    callback.onSuccess(topicFilter, (String) msg.obj);
                    break;
                //失败
                case 1:
                    callback.onError(topicFilter, (Throwable) msg.obj);
                    break;
            }
        }
    };
}
