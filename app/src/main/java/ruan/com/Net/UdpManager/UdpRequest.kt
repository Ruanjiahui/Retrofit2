package ruan.com.Net.UdpManager

import android.content.Context
import android.location.Address
import android.os.Looper
import ruan.com.Net.BaseNetCallback
import ruan.com.Net.UdpManager.Interface.UdpCallback
import ruan.com.Net.UdpManager.Settings.Companion.depay_time_out
import ruan.com.Net.UdpManager.Settings.Companion.time_out
import java.util.*

/**
 * Created by Administrator on 2018/5/16.
 */
class UdpRequest : BaseRequest {

    private var callback : UdpCallback.Response? = null
    //发送次数，默认发送一次
    private var count = Settings.count
    //定时器
    private var timer : Timer? = null
    //统计次数
    private var total = 0

    constructor(context: Context? , callback: UdpCallback.Response?) : super(context , null){
        this.callback = callback
    }

    constructor(context: Context? , callback: UdpCallback.Response? , count : Int) : this(context , null){
        this.count = count
        this.callback = callback
    }

    constructor(context: Context? , callback: UdpCallback.Response? , netCallback: BaseNetCallback?) : super(context , netCallback){
        this.callback = callback
    }

    constructor(context: Context? , callback: UdpCallback.Response? , netCallback: BaseNetCallback? , count: Int) : this(context, callback, netCallback){
        this.count = count
    }

    /**
     * 初始化
     */
    fun init(){
        reviced(object : BaseRequest.ObservableCallback{

            override fun onSuccess(requestCode: Int?, msg: String?) {
                timer?.cancel()
                callback?.onUdpSuccess(requestCode , msg)
            }

            override fun onError(requestCode: Int?, throwable: Throwable? , code : Int?) {
                timer?.cancel()
                callback?.onUdpError(requestCode , throwable , code)
            }
        })
    }

    /**
     * 发送数据
     *
     * @param msg 发送数据
     * @param PORT 发送端口
     * @param netAddress 发送地址
     * @param requestCode 发送标识
     */
    fun send(msg : ByteArray , PORT : Int , netAddress: String? , requestCode : Int?){
        timer = Timer()
        timer?.schedule(object : TimerTask(){
            override fun run() {
                Looper.prepare()
                isOut()
                send(msg , netAddress , PORT , requestCode!!)
                Looper.loop()
            }
        } , depay_time_out , time_out)

    }

    /**
     * 是否达到循环次数
     */
    private fun isOut(){
        total++
        if (total <= count)
            return

        timer?.cancel()
        return
    }

}