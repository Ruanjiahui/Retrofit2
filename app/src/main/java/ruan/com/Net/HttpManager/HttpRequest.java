package ruan.com.Net.HttpManager;

import android.content.Context;

import ruan.com.Net.NetLog;
import ruan.com.retrofit2.BaseObject;
import ruan.com.Net.BaseNetCallback;
import ruan.com.Net.HttpManager.Interface.HttpCallback;
import rx.Observable;

/**
 * Http 发送数据和接收数据的实体类
 * Created by 19820 on 2018/1/8.
 */

public class HttpRequest extends BaseRequest {

    private HttpCallback.Response callResponse;

    public HttpRequest(Context context , HttpCallback.Response callResponse){
        super(context , null);
        this.callResponse = callResponse;
    }

    public HttpRequest(Context context , HttpCallback.Response callResponse , BaseNetCallback netCallback){
        super(context , netCallback);
        this.callResponse = callResponse;
    }

    public void request(Observable<?> observable, int requestCode){
        Observable<HttpResponse> tObservable = (Observable<HttpResponse>) observable;
        //请求访问获取数据
        request(tObservable, requestCode, new ObservableCallback() {
            @Override
            public void onSuccess(int requestCode, HttpResponse response) {
                callResponse.onSuccess(requestCode , response);
            }

            @Override
            public void onError(int requestCode, Throwable throwable) {
                callResponse.onError(requestCode , throwable);
            }
        });
    }
}
