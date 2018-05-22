package ruan.com.Net.MqttManager.Interface

/**
 * Created by Administrator on 2018/5/4.
 */
interface MqttCallback {

    interface Response {
        /**
         * 请求成功
         * @param topic       请求标识
         * @param message        请求返回的数据
         */
        fun onSuccess(topic: String?, message: String?)

        /**
         * 请求失败
         * @param topic       请求标识
         * @param throwable         请求失败的对象
         */
        fun onError(topic: String?, throwable: Throwable?)

    }
}