package ruan.com.Net.MqttManager

import android.content.Context
import org.eclipse.paho.client.mqttv3.MqttClient
import ruan.com.Net.BaseNetCallback
import ruan.com.Net.MqttManager.Interface.MqttCallback

@Suppress("UNREACHABLE_CODE")
/**
 * Created by Administrator on 2018/5/4.
 */
class MqttRequest : BaseRequest{

    private var mqttClient : MqttClient? = null
    private var response : MqttCallback.Response? = null


    constructor(context: Context , netCallback: BaseNetCallback? , response: MqttCallback.Response) : super(context , netCallback){
        this.response = response
    }

    constructor(context: Context , response: MqttCallback.Response) : this(context , null!! , response)

    /**
     * 请求链接mqtt服务器
     * @param topicFilter
     */
    fun init(topicFilter : String?){
        mqttClient = init(topicFilter , object : BaseRequest.ObservableCallback{

            override fun onSuccess(topic: String?, message: String?) {
                response?.onSuccess(topic , message)
            }

            override fun onError(topic: String?, throwable: Throwable?) {
                response?.onError(topic , throwable)
            }
        })
        if (mqttClient == null)
            return
    }

    /**
     * 发送数据到服务器
     * @param topicFilter       订阅标识
     * @param message           发送数据
     */
    fun send(topicFilter : String? , message : String?){
        request(mqttClient , topicFilter , message , object : ObservableCallback{
            override fun onSuccess(topic: String?, message: String?) {
                response?.onSuccess(topic , message)
            }

            override fun onError(topic: String?, throwable: Throwable?) {
                response?.onError(topic , throwable);
            }
        })
    }

}