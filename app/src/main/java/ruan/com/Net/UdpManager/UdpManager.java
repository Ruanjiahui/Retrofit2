package ruan.com.Net.UdpManager;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * UDP的基类
 * Created by 19820 on 2018/3/25.
 */

public class UdpManager {

    private String sendStr = "SendString";
    private String netAddress = "127.0.0.1";
    private final int PORT_NUM = 5066;

    private static DatagramSocket datagramSocket;
    private DatagramPacket datagramPacket;

    /**
     * 单例模式创建udp请求对象
     * @return
     */
    public synchronized static DatagramSocket getInstance(){
        if (datagramSocket == null || datagramSocket.isConnected() || datagramSocket.isClosed()){
            // 初始化datagramSocket,注意与前面Server端实现的差别
            try {
                datagramSocket = new DatagramSocket();
            } catch (SocketException e) {
                return null;
            }
        }
        return datagramSocket;
    }

    /**
     * 关闭连接
     */
    public static void close(){
        if (datagramSocket != null){
            datagramSocket.disconnect();
            datagramSocket.close();
        }
    }

}
