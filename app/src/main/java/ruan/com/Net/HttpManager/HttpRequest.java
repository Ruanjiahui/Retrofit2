package ruan.com.Net.HttpManager;

import android.content.Context;

import ruan.com.retrofit2.BaseObject;
import ruan.com.Net.BaseNetCallback;
import ruan.com.Net.HttpManager.Interface.HttpCallback;
import rx.Observable;

/**
 * Http 发送数据和接收数据的实体类
 * Created by 19820 on 2018/1/8.
 */

public class HttpRequest extends BaseRequest {

    private HttpCallback.Response response;

    public HttpRequest(Context context , HttpCallback.Response response){
        super(context , null);
        this.response = response;
    }

    public HttpRequest(Context context , HttpCallback.Response response , BaseNetCallback netCallback){
        super(context , netCallback);
        this.response = response;
    }

    public void request(Observable<?> observable, int requestCode){
        Observable<BaseObject> tObservable = (Observable<BaseObject>) observable;
        //请求访问获取数据
        request(tObservable, requestCode, new ObservableCallback() {
            @Override
            public void onSuccess(int requestCode, BaseObject baseObject) {
                response.onSuccess(requestCode , baseObject);
            }

            @Override
            public void onError(int requestCode, Throwable throwable) {
                response.onError(requestCode , throwable);
            }
        });
    }
}
