package ruan.com.Net.HttpManager

import android.content.Context
import ruan.com.Net.BaseNetCallback
import ruan.com.Net.HttpManager.Interface.HttpCallback
import rx.Observable

@Suppress("UNREACHABLE_CODE")
/**
 * Created by Administrator on 2018/5/4.
 */
class HttpRequest : BaseRequest{

    private var callResponse : HttpCallback.Response? = null

    constructor(context: Context , callResponse: HttpCallback.Response) : this(context , callResponse , null!!)

    constructor(context: Context , callResponse: HttpCallback.Response , netCallback: BaseNetCallback?) : super(context , netCallback){
        this.callResponse = callResponse
    }

    fun request(observable : Observable<*> , requestCode : Int){
        val tObservable = observable as Observable<HttpResponse>
        //请求访问获取数据
        request(tObservable , requestCode , object : BaseRequest.ObservableCallback {

            override fun onSuccess(requestCode: Int, response: HttpResponse?) {
                callResponse?.onSuccess(requestCode, response)
            }

            override fun onError(requestCode: Int, throwable: Throwable?) {
                callResponse?.onError(requestCode , throwable)
            }
        })
    }
}