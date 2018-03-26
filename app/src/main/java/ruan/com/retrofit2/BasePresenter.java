package ruan.com.retrofit2;

import java.util.Arrays;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by tanxinye on 2016/10/23.
 */
public class BasePresenter implements BaseRequestCallback {

//    private CompositeSubscription compositeSubscription;
//    private BaseResponseCallback callback;
//
//    protected interface ObservableCallback<T> {
//
//        void onSuccess(T response);
//    }
//
//    public BasePresenter(BaseResponseCallback callback) {
//        this.callback = callback;
//    }
//
//    protected <T> void execute(Observable<Object> observable, final Class<T> cls, final ObservableCallback<T> observableCallback) {
//        if (!NetWorkUtil.isNetWorkAvailable()) {
//            callback.noConnected();
//            return;
//        }
//        Subscription subscribe = observable.subscribeOn(Schedulers.newThread()).observeOn(Schedulers.io()).subscribe(new Observer<Object>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                callback.requestFailure();
//                LogUtil.e("Presenter onError" + getClass().getSimpleName(), e.getClass().getSimpleName() + ":" + e.toString());
//                System.out.println("Presenter onError:" + e.toString());
//            }
//
//            @Override
//            public void onNext(Object object) {
//                callback.requestFinish();
////                System.out.println("onNext:" + encodeData.getList());
////                T response =  GsonUtil.getInstance().decodeJson(encodeData.getList(), cls);
////                LogUtil.e("parseData" + response.getClass().getSimpleName(),GsonUtil.getInstance().toJson(response));
////                observableCallback.onSuccess(response);
//            }
//        });
//        addSubscription(subscribe);
//    }
//
//    protected void addSubscription(Subscription s) {
//        if (this.compositeSubscription == null) {
//            this.compositeSubscription = new CompositeSubscription();
//        }
//        this.compositeSubscription.add(s);
//    }
//
    @Override
    public void unsubcrible() {
//        if (this.compositeSubscription != null) {
//            this.compositeSubscription.unsubscribe();
//        }
    }
}
