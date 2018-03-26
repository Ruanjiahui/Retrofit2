package ruan.com.Net.HttpManager.Interface;

import ruan.com.retrofit2.BaseObject;

/**
 * Created by 19820 on 2018/1/8.
 */

public interface HttpCallback{


    interface Response{
        /**
         * 请求成功
         * @param requestCode       请求标识
         * @param baseObject        请求返回的对象
         */
        void onSuccess(int requestCode , BaseObject baseObject);

        /**
         * 请求失败
         * @param requestCode       请求标识
         * @param throwable         请求失败的对象
         */
        void onError(int requestCode , Throwable throwable);

    }
}
