package ruan.com.Net.HttpManager

/**
 * Created by Administrator on 2018/5/4.
 */
class Settings {

    companion object {
        /**
         * 测试服务器地址
         */
        val DEBUG_HOST : String = "http://39.108.192.191:8855/AppDebug/"

        /**
         * 正式服务器地址
         */
        val RELEASE_HOST : String = ""

        /**
         * 网络提示语
         */
        val net_error : String = "当前网络不可用"

    }

}