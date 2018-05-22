package ruan.com.Net.MqttManager

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Message
import android.widget.Toast
import org.eclipse.paho.client.mqttv3.*
import ruan.com.Net.BaseNetCallback
import ruan.com.Net.MqttManager.Settings.Companion.CONNECT_MSG
import ruan.com.Net.MqttManager.Settings.Companion.NET_MSG
import ruan.com.Net.NetLog
import ruan.com.Net.NetWorkUtil
import java.nio.charset.Charset

/**
 * Created by Administrator on 2018/5/4.
 */
open class BaseRequest(private var context: Context?, private var netCallback: BaseNetCallback?){

    private var callback : BaseRequest.ObservableCallback? = null
    private var topicFilter : String? = null

    private val success : Int? = 0
    private val fail : Int? = 0


    protected interface ObservableCallback{

        fun onSuccess(topic : String? , message : String?)

        fun onError(topic : String? , throwable: Throwable?)
    }

    /**
     * 初始化链接mqtt的方法
     *
     * @param topicFilter 订阅主题
     * @param callback    回调接口
     * @return
     */
    protected fun init(topicFilter : String? , callback : BaseRequest.ObservableCallback?) : MqttClient?{
        this.callback = callback
        this.topicFilter = topicFilter

        //没有网络直接提示
        if (!NetWorkUtil.isNetWorkAvailable(context)) {

            if (netCallback != null)
                netCallback?.noConnect()
            else
                Toast.makeText(context, NET_MSG, Toast.LENGTH_SHORT).show()
            return null!!
        }

        try {
            val mqttClient = MqttManager.getInstance()

            mqttClient?.setCallback(object : MqttCallback{
                /**
                 * This method is called when a message arrives from the server.
                 *
                 *
                 *
                 * This method is invoked synchronously by the MQTT client. An
                 * acknowledgment is not sent back to the server until this
                 * method returns cleanly.
                 *
                 *
                 * If an implementation of this method throws an `Exception`, then the
                 * client will be shut down.  When the client is next re-connected, any QoS
                 * 1 or 2 messages will be redelivered by the server.
                 *
                 *
                 * Any additional messages which arrive while an
                 * implementation of this method is running, will build up in memory, and
                 * will then back up on the network.
                 *
                 *
                 * If an application needs to persist data, then it
                 * should ensure the data is persisted prior to returning from this method, as
                 * after returning from this method, the message is considered to have been
                 * delivered, and will not be reproducible.
                 *
                 *
                 * It is possible to send a new message within an implementation of this callback
                 * (for example, a response to this message), but the implementation must not
                 * disconnect the client, as it will be impossible to send an acknowledgment for
                 * the message being processed, and a deadlock will occur.
                 *
                 * @param topic name of the topic on the message was published to
                 * @param message the actual message.
                 * @throws Exception if a terminal error has occurred, and the client should be
                 * shut down.
                 */
                override fun messageArrived(topic: String?, message: MqttMessage?) {
                    NetLog.info(String(message?.payload!! , Charset.forName("utf-8")))

                    val msg = Message()
                    msg.arg1 = success!!
                    msg.obj = String(message.payload , Charset.forName("utf-8"))
                    handler.sendMessage(msg)
                }

                /**
                 * This method is called when the connection to the server is lost.
                 *
                 * @param cause the reason behind the loss of connection.
                 */
                override fun connectionLost(cause: Throwable?) {
                    NetLog.info(cause?.toString())

                    val msg = Message()
                    msg.arg1 = fail!!
                    msg.obj = cause
                    handler.sendMessage(msg)
                }

                /**
                 * Called when delivery for a message has been completed, and all
                 * acknowledgments have been received. For QoS 0 messages it is
                 * called once the message has been handed to the network for
                 * delivery. For QoS 1 it is called when PUBACK is received and
                 * for QoS 2 when PUBCOMP is received. The token will be the same
                 * token as that returned when the message was published.
                 *
                 * @param token the delivery token associated with the message.
                 */
                override fun deliveryComplete(token: IMqttDeliveryToken?) {
                    //没有发送成功则重新发送
                    val total = 0
                    while (!token?.isComplete!!) {
                        if (total < Settings.count) {
                            //重新发送

                        } else {
                            //重发还是失败则返回错误
                            val msg = Message()
                            msg.arg1 = fail!!
                            msg.obj = token.exception
                            handler.sendMessage(msg)
                            break
                        }
                    }
                }
            })
            mqttClient?.connect()
            //订阅频道
            mqttClient?.subscribe(topicFilter)

            return mqttClient
        } catch (e : MqttException){
            NetLog.info(e.toString())
            Toast.makeText(context, CONNECT_MSG, Toast.LENGTH_SHORT).show()
            return null!!
        }


    }

    /**
     * 发送数据
     *
     * @param mqttClient  发送数据的对象
     * @param topicFilter 发布主题
     * @param message     发送信息
     */
    protected fun request(mqttClient: MqttClient? , topicFilter: String? , message: String? , callback: ObservableCallback?){
        this.callback = callback
        this.topicFilter = topicFilter

        //创建发送数据的结构体
        val mqttMessage = MqttMessage()
        mqttMessage.payload = message?.toByteArray(Charset.forName("utf-8"))
        try {
            mqttClient?.publish(topicFilter, mqttMessage)
        } catch (e: MqttException) {
            NetLog.info(e.toString())
            callback?.onError(topicFilter, e)
        }

    }


    @SuppressLint("HandlerLeak")
    private val handler = object : Handler() {

        override fun dispatchMessage(msg: Message) {
            super.dispatchMessage(msg)
            when (msg.arg1) {
            //成功
                success -> callback?.onSuccess(topicFilter, msg.obj as String)
            //失败
                fail -> callback?.onError(topicFilter, msg.obj as Throwable)
            }
        }
    }

}