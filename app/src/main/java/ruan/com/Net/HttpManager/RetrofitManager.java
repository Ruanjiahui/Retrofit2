package ruan.com.Net.HttpManager;

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
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .baseUrl(DEBUG_HOST)
                    .build();
            return retrofit;
        }
        return retrofit;
    }
}
