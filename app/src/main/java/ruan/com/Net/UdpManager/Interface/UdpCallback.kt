package ruan.com.Net.UdpManager.Interface

/**
 * Created by Administrator on 2018/5/15.
 */
interface UdpCallback {

    interface Response{

        /**
         * 请求成功
         * @param requestCode       请求标识
         * @param msg               请求返回的数据
         */
        fun onUdpSuccess(requestCode : Int? , msg : String?)

        /**
         * 请求失败
         * @param requestCode       请求标识
         * @param throwable         请求失败的对象
         */
        fun onUdpError(requestCode : Int? , throwable : Throwable? , code : Int?)

    }
}