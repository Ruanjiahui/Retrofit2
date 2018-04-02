package ruan.com.Net.MqttManager.Interface;

import ruan.com.Net.HttpManager.HttpResponse;

/**
 * Created by 19820 on 2018/1/8.
 */

public interface MqttCallback {


    interface Response{
        /**
         * 请求成功
         * @param requestCode       请求标识
         * @param message        请求返回的数据
         */
        void onSuccess(String requestCode, String message);

        /**
         * 请求失败
         * @param requestCode       请求标识
         * @param throwable         请求失败的对象
         */
        void onError(String requestCode, Throwable throwable);

    }
}
