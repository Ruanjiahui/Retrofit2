package ruan.com.Net.HttpManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static ruan.com.Net.HttpManager.Settings.*;

/**
 * Created by 19820 on 2018/3/25.
 */

public class RetrofitManager {


    private static Retrofit retrofit;

    /**
     * 实例化retrofit请求对象
     * @return
     */
    public synchronized static Retrofit getInstance(){
        if (retrofit == null){
            //声明日志类
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            //设定日志级别
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            //自定义OkHttpClient
            OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
            //添加拦截器
            okHttpClient.addInterceptor(httpLoggingInterceptor);

            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .baseUrl(DEBUG_HOST)
                    .client(okHttpClient.build())
                    .build();
            return retrofit;
        }
        return retrofit;
    }
}
