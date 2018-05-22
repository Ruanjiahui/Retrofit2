package ruan.com.Net.HttpManager

import android.content.Context
import android.widget.Toast
import ruan.com.Net.BaseNetCallback
import ruan.com.Net.HttpManager.Settings.Companion.net_error
import ruan.com.Net.NetLog
import ruan.com.Net.NetWorkUtil
import rx.Observable
import rx.Observer
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by Administrator on 2018/5/4.
 */
open class BaseRequest(context: Context, private var netCallback: BaseNetCallback?){

    private var subscription: Subscription? = null
    private var context: Context? = context


    protected interface ObservableCallback {

        fun onSuccess(requestCode: Int, response: HttpResponse?)

        fun onError(requestCode: Int, throwable: Throwable?)

    }

    /**
     * 请求网络的方法
     *
     * @param observable
     * @param requestCode
     * @param callback
     */
    protected fun request(observable: Observable<HttpResponse>, requestCode: Int, callback: ObservableCallback) {
        //判断是否联网
        if (!NetWorkUtil.isNetWorkAvailable(context)) {
            //如果回调接口不为空则回调回去
            if (netCallback != null)
                netCallback?.noConnect()
            //回调接口为空则直接调试网络断开
            else
                Toast.makeText(context, net_error, Toast.LENGTH_LONG).show()
            return
        }

        subscription = observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(object : Observer<HttpResponse>{
            /**
             * Notifies the Observer that the [Observable] has finished sending push-based notifications.
             *
             *
             * The [Observable] will not call this method if it calls [.onError].
             */
            override fun onCompleted() {
                    netCallback?.onCompleted()
            }

            /**
             * Notifies the Observer that the [Observable] has experienced an error condition.
             *
             *
             * If the [Observable] calls this method, it will not thereafter call [.onNext] or
             * [.onCompleted].
             *
             * @param e
             * the exception encountered by the Observable
             */
            override fun onError(e: Throwable?) {
                NetLog.info(e.toString())
                callback.onError(requestCode, e)
            }

            /**
             * Provides the Observer with a new item to observe.
             *
             *
             * The [Observable] may call this method 0 or more times.
             *
             *
             * The `Observable` will not call this method again after it calls either [.onCompleted] or
             * [.onError].
             *
             * @param t
             * the item emitted by the Observable
             */
            override fun onNext(t: HttpResponse?) {
                NetLog.info(t.toString())
                callback.onSuccess(requestCode , t)
            }

        })
    }

    /**
     * 取消请求
     */
    fun cancel(){
        if(subscription != null && subscription?.isUnsubscribed == true){
            subscription?.unsubscribe()
        }
    }

}