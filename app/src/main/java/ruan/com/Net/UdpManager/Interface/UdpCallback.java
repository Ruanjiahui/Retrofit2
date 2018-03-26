package ruan.com.Net.UdpManager.Interface;

import ruan.com.retrofit2.BaseObject;

/**
 * Created by 19820 on 2018/3/25.
 */

public interface UdpCallback {

    interface Response{
        /**
         * 请求成功
         * @param requestCode       请求标识
         * @param msg               请求返回的数据
         */
        void onUdpSuccess(int requestCode , String msg);

        /**
         * 请求失败
         * @param requestCode       请求标识
         * @param throwable         请求失败的对象
         */
        void onUdpError(int requestCode , Throwable throwable);

    }

}
