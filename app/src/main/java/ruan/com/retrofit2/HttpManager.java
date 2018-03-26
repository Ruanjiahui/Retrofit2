package ruan.com.retrofit2;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 19820 on 2018/1/8.
 *
 * 实例化 请求网络的组件
 *
 *
 */

public class HttpManager {

    private static HttpManager httpManager;

    private Retrofit retrofit;

    public static synchronized HttpManager getInstance(){
        if (httpManager == null) {
            synchronized (HttpManager.class) {
                if (httpManager == null) {
                    httpManager = new HttpManager();
                }
            }
        }
        return httpManager;
    }

    public HttpManager(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient okHttpClient = builder.retryOnConnectionFailure(true)
                .connectTimeout(20, TimeUnit.SECONDS)
//                .addNetworkInterceptor(encodeInterceptor)
//                .addNetworkInterceptor(addCookiesInterceptor)
//                .addNetworkInterceptor(receivedCookiesInterceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl("http://39.108.144.9:8855/fanghuaLaw/")
                .build();
    }

    /**
     * 返回请求的对象
     * @return
     */
    public Retrofit getRetrofit(){
        return retrofit;
    }
}
