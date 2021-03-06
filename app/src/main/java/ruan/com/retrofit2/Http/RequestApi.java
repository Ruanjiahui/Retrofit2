package ruan.com.retrofit2.Http;

import android.content.Context;

import retrofit2.Retrofit;
import ruan.com.Net.HttpManager.HttpRequest;
import ruan.com.Net.BaseNetCallback;
import ruan.com.Net.HttpManager.HttpResponse;
import ruan.com.Net.HttpManager.Interface.HttpCallback;
import ruan.com.Net.HttpManager.RetrofitManager;
import ruan.com.retrofit2.BugUploadBean;
import ruan.com.retrofit2.EncodeData;
import ruan.com.retrofit2.Http.Service.UploadBugService;
import ruan.com.retrofit2.Http.Service.UserService;
import rx.Observable;

/**
 * Created by 19820 on 2018/3/25.
 */

public class RequestApi {

    private Retrofit retrofit;
    private Context context;
    private BaseNetCallback netCallback = null;

    public RequestApi(Context context){
        this.context = context;
        retrofit = RetrofitManager.Companion.getInstance();
    }

    public RequestApi(Context context , BaseNetCallback netCallback){
        this.context = context;
        this.netCallback = netCallback;
        retrofit = RetrofitManager.Companion.getInstance();
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

    /**
     * 上传bug
     * @param bugUploadBean
     * @param callback
     * @param requestCode
     */
    public void UploadBug(BugUploadBean bugUploadBean , HttpCallback.Response callback , int requestCode){
        UploadBugService apiService = retrofit.create(UploadBugService.class);
        Observable<?> observable = apiService.commitBug(bugUploadBean);
        //请求获取数据
        new HttpRequest(context , callback , netCallback).request(observable , requestCode);
    }
}
