package ruan.com.Net.UdpManager

import java.net.DatagramSocket
import java.net.SocketException

/**
 * Created by Administrator on 2018/5/16.
 */
class UdpManager {

    private val sendStr = "SendString"

    private val netAddress = "127.0.0.1";

    private val PORT_NUM = 5066

    companion object {
        private var datagramSocket : DatagramSocket? = null

        @Synchronized
        public fun getInstance() : DatagramSocket? {
            if (datagramSocket == null || datagramSocket!!.isConnected || datagramSocket!!.isClosed){
                // 初始化datagramSocket,注意与前面Server端实现的差别
                try {
                    datagramSocket = DatagramSocket()
                } catch (e: SocketException) {
                    return null
                }
            }
            return datagramSocket
        }

        /**
         * 关闭连接
         */
        public fun close(){
            datagramSocket?.disconnect()
            datagramSocket?.close()
        }

    }


}