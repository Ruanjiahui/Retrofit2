package ruan.com.Net.UdpManager

/**
 * Created by Administrator on 2018/5/15.
 */
class Settings {

    companion object {

        /**
         * 网络发生错误的时候提醒
         */
        val net_error = "当前wifi已断开，请连接后再尝试使用"

        //设置超时2秒和每次2秒请求一次
        val time_out : Long = 2000

        //设置延迟500毫秒发送
        val depay_time_out : Long = 0

        //设置接收超时
        val reviced_time_out :Long = 10000

        //发送次数
        val count = 2

        val receBuf : ByteArray? = ByteArray(1024)

    }

}