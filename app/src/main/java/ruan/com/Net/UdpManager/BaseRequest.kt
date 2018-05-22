package ruan.com.Net.UdpManager

import android.content.Context
import android.os.Handler
import android.os.Message
import android.widget.Toast
import ruan.com.Net.BaseNetCallback
import ruan.com.Net.Code.Companion.revice_fail
import ruan.com.Net.Code.Companion.revice_time_out
import ruan.com.Net.Code.Companion.send_fail
import ruan.com.Net.HttpManager.Settings.Companion.net_error
import ruan.com.Net.NetWorkUtil
import ruan.com.Net.UdpManager.Settings.*
import ruan.com.Net.UdpManager.Settings.Companion.receBuf
import ruan.com.Net.UdpManager.Settings.Companion.reviced_time_out
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

/**
 * Created by Administrator on 2018/5/15.
 */
open class BaseRequest(private val context: Context?, private val netCallback: BaseNetCallback?) {

    private var requestCode : Int? = 0

    private var sendThread : Thread? = null

    public interface ObservableCallback {

        fun onSuccess(requestCode: Int?, msg: String?)

        fun onError(requestCode: Int?, throwable: Throwable? , code : Int?)
    }


    protected fun send(msg: ByteArray?, netAddress: String?, PORT: Int, requestCode: Int) {
        this.requestCode = requestCode;
        //发送前，首先判断wifi是否连接
        if (!NetWorkUtil.isWifiConnected(context)) {
            //如果回调接口不为空则回调回去
            if (netCallback != null)
                netCallback.noConnect()
            //回调接口为空则直接调试网络断开
            else
                Toast.makeText(context, net_error, Toast.LENGTH_SHORT).show()
            return
        }

        //获取发送数据的对象
        val datagramSocket = UdpManager.getInstance()
        try {
            //创建发送地址
            val address = InetAddress.getByName(netAddress)
            //创建发送数据对象
            //发送数据对象为空则直接返回空
            val datagramPacket = DatagramPacket(msg, msg!!.size, address, PORT) ?: return

            datagramSocket?.send(datagramPacket)

            //开启线程前  先停止线程
            if (sendThread != null)
                sendThread?.interrupt()
            //开启线程
            sendThread = Thread(Runnable {
                try {
                    Thread.sleep(reviced_time_out)
                } catch (e : Exception){
                    System.out.println("线程停止")
                    return@Runnable
                }
                //接收超时
                val message = Message()
                message.what = 1
                message.arg1 = requestCode
                message.arg2 = revice_time_out
                revicedHandler.sendMessage(message)
            })


        } catch (e: Exception) {
            val message = Message()
            message.what = 1
            message.arg1 = requestCode
            message.obj = e
            message.arg2 = send_fail
            revicedHandler.sendMessage(message)
        }

    }

    /**
     * 接受数据
     *
     * @param receBuf 接受数据的数据
     * @param PORT    接受数据的端口
     */
    fun reviced(callback: ObservableCallback) {
        //发送前，首先判断wifi是否连接
        if (!NetWorkUtil.isWifiConnected(context)) {
            //如果回调接口不为空则回调回去
            if (netCallback != null)
                netCallback.noConnect()
            else
                Toast.makeText(context, net_error, Toast.LENGTH_SHORT).show()//回调接口为空则直接调试网络断开
            return
        }
        //接收数据前先终止线程
        revThread?.interrupt()

        this.revicedcallback = callback
        revThread = Thread(Runnable {
            //添加无限监听
            while (true) {
                var datagramSocket: DatagramSocket? = null
                try {
                    datagramSocket = UdpManager.getInstance()
                    //设置超时时间10秒
                    assert(datagramSocket != null)
//                    datagramSocket?.soTimeout = reviced_time_out
                    val recePacket = DatagramPacket(receBuf, receBuf!!.size)
                    datagramSocket?.receive(recePacket)

                    val msg = String(recePacket.data, 0, recePacket.length)
                    //接收到数据则关闭
                    //datagramSocket.disconnect();
                    //datagramSocket.close();

                    //停止线程
                    if (sendThread != null)
                        sendThread?.interrupt()

                    val message = Message()
                    message.what = 0
                    message.arg1 = requestCode!!
                    message.obj = msg
                    revicedHandler.sendMessage(message)
                } catch (e: Exception) {
                    //如果接收错误则关闭连接
                    if (datagramSocket != null) {
                        datagramSocket.disconnect()
                        datagramSocket.close()
                    }
                    val message = Message()
                    message.what = 1
                    message.arg1 = requestCode!!
                    message.obj = e
                    message.arg2 = revice_fail
                    revicedHandler.sendMessage(message)
                }

            }
        })
        revThread?.start()
    }

    private var revThread: Thread? = null
    private var revicedcallback: ObservableCallback? = null

    /**
     * 设置接收数据接口
     */
    private fun setReviceCallback(revicedcallback: ObservableCallback?) {
        this.revicedcallback = revicedcallback
    }

    /**
     * 关闭接收
     */
    protected fun closeRevice() {
        revThread?.interrupt()
    }

    private val revicedHandler = object : Handler() {

        override fun dispatchMessage(msg: Message?) {
            if (revicedcallback != null) {
                val requestCode = msg?.arg1
                when (msg?.what) {
                //成功
                    0 -> revicedcallback?.onSuccess(requestCode, msg.obj as String)
                //失败
                    1 -> revicedcallback?.onError(requestCode, msg.obj as Throwable , msg.arg2)
                }
            }
        }

    }
}