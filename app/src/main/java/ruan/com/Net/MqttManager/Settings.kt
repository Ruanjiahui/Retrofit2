package ruan.com.Net.MqttManager

/**
 * Created by Administrator on 2018/5/4.
 */
class Settings {

    companion object {

        //MQTT链接的地址
        var MQTT_IP = "tcp://my5101.com:1883"

        //重新发送次数
        var count = 1

        //没有网络提示
        var NET_MSG = "当前网络不可用，请稍后尝试"

        //链接服务器失败
        var CONNECT_MSG = "连接服务器失败"
    }

}