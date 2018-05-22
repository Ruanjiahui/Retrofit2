package ruan.com.Net.HttpManager.Interface

import ruan.com.Net.HttpManager.HttpResponse

/**
 * Created by Administrator on 2018/5/4.
 */
interface HttpCallback {

    interface Response{
        /**
         * 返回成功
         *
         * @param  requestCode  返回码
         * @param  response     返回对象
         */
        fun onSuccess(requestCode: Int, response: HttpResponse?)

        /**
         * 返回失败
         *
         * @param  requestCode  返回码
         * @param  throwable    返回错误
         */
        fun onError(requestCode: Int , throwable: Throwable?)

    }

}