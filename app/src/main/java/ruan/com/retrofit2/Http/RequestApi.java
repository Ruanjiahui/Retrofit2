package ruan.com.retrofit2.Http;

import android.content.Context;

import retrofit2.Retrofit;
import ruan.com.Net.HttpManager.HttpRequest;
import ruan.com.Net.BaseNetCallback;
import ruan.com.Net.HttpManager.HttpResponse;
import ruan.com.Net.HttpManager.Interface.HttpCallback;
import ruan.com.Net.HttpManager.RetrofitManager;
import ruan.com.retrofit2.EncodeData;
import ruan.com.retrofit2.Http.Service.UserService;
import rx.Observable;

/**
 * Created by 19820 on 2018/3/25.
 */

public class RequestApi {

    private Retrofit retrofit;
    private Context context;
    private BaseNetCallback netCallback;

    public RequestApi(Context context){
        this.context = context;
        retrofit = RetrofitManager.getInstance();
    }

    public RequestApi(Context context , BaseNetCallback netCallback){
        this.context = context;
        this.netCallback = netCallback;
        retrofit = RetrofitManager.getInstance();
    }

    /**
     * 请求获取文章
     * @param callback
     */
    public void RequestTopic(HttpCallback.Response callback , int requestCode){
        UserService apiService = retrofit.create(UserService.class);
        Observable<?> observable = apiService.gettopicpage(1 , 0);
        //请求获取数据
        new HttpRequest(context , callback , netCallback).request(observable , requestCode);
    }

}
