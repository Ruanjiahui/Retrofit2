package ruan.com.Net.HttpManager

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import ruan.com.Net.HttpManager.Settings.Companion.DEBUG_HOST

/**
 * Created by Administrator on 2018/5/4.
 */
class RetrofitManager {

    companion object {
        var retrofit: Retrofit? = null
        //初始化 retrofit
        fun getInstance(): Retrofit? {
            //判断retrofit 是否为空
            if (retrofit == null) {
                //初始化 打印okhttp日志配置
                val httpLoggingInterceptor = HttpLoggingInterceptor()
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

                val okHttpClient = OkHttpClient.Builder()
                okHttpClient.addInterceptor(httpLoggingInterceptor)

                retrofit = Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .baseUrl(DEBUG_HOST)
                        .client(okHttpClient.build())
                        .build()
            }
            return retrofit
        }


        fun getInstance(DEBUG_HOST: String): Retrofit? {
            //初始化 打印okhttp日志配置
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val okHttpClient = OkHttpClient.Builder()
            okHttpClient.addInterceptor(httpLoggingInterceptor)

            retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .baseUrl(DEBUG_HOST)
                    .client(okHttpClient.build())
                    .build()
            return retrofit
        }
    }
}