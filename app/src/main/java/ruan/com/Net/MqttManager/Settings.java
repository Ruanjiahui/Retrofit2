package ruan.com.Net.MqttManager;

/**
 * MQTT 配置文件
 *
 * Created by Administrator on 2018/4/2.
 */

public class Settings {

    //MQTT链接的地址
    public static String MQTT_IP = "tcp://192.168.102.118:1883";

    //重新发送次数
    public static int count = 1;

    //没有网络提示
    public static String NET_MSG = "当前网络不可用，请稍后尝试";

    //链接服务器失败
    public static String CONNECT_MSG = "连接服务器失败";

}
