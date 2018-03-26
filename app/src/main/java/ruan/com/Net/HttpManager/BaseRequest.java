package ruan.com.Net.HttpManager;

import android.content.Context;
import android.widget.Toast;

import ruan.com.Net.NetWorkUtil;
import ruan.com.retrofit2.BaseObject;
import ruan.com.Net.BaseNetCallback;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.schedulers.Schedulers;

import static ruan.com.Net.HttpManager.Settings.*;

/**
 * Created by 19820 on 2018/1/8.
 */

public class BaseRequest {

    private Subscription subscription;
    private BaseNetCallback netCallback;
    private Context context;

    public BaseRequest(Context context , BaseNetCallback netCallback) {
        this.context = context;
        this.netCallback = netCallback;
    }

    protected interface ObservableCallback {

        void onSuccess(int requestCode, BaseObject baseObject);


        void onError(int requestCode, Throwable throwable);

    }

    /**
     * 请求网络的方法
     *
     * @param observable
     * @param requestCode
     * @param callback
     */
    protected void request(Observable<BaseObject> observable, final int requestCode, final ObservableCallback callback) {
        //判断是否联网
        if (!NetWorkUtil.isNetWorkAvailable(context)) {
            //如果回调接口不为空则回调回去
            if (netCallback != null)
                netCallback.noConnect();
            //回调接口为空则直接调试网络断开
            else
                Toast.makeText(context , net_error , Toast.LENGTH_SHORT).show();

            return;
        }

        subscription = observable.subscribeOn(Schedulers.newThread()).observeOn(Schedulers.io()).subscribe(new Observer<BaseObject>() {

            @Override
            public void onCompleted() {
                if (netCallback != null)
                    netCallback.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                callback.onError(requestCode, e);
            }

            @Override
            public void onNext(BaseObject baseObject) {
                callback.onSuccess(requestCode, baseObject);
            }
        });
    }

    /**
     * 取消请求
     */
    public void cancel() {
        if (subscription != null && subscription.isUnsubscribed())
            subscription.unsubscribe();
    }

}
