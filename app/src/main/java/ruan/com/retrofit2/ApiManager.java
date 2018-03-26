package ruan.com.retrofit2;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ruan.com.retrofit2.Http.Service.UserService;


/**
 * Created by tanxinye on 2016/10/23.
 * <p>
 * http請求實例化類
 */
public class ApiManager {

    private static ApiManager apiManager;
    private OkHttpClient okHttpClient;
    private Retrofit retrofit;

    /**
     * 实例化okhttp和retrofit
     */
    private ApiManager() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        okHttpClient = builder.retryOnConnectionFailure(true)
                .connectTimeout(20, TimeUnit.SECONDS)
//                .addNetworkInterceptor(encodeInterceptor)
//                .addNetworkInterceptor(addCookiesInterceptor)
//                .addNetworkInterceptor(receivedCookiesInterceptor)
                .build();
        retrofit = new Retrofit.Builder().baseUrl(Config.SERVER_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ApiManager getInstance() {
        if (apiManager == null) {
            synchronized (ApiManager.class) {
                if (apiManager == null) {
                    apiManager = new ApiManager();
                }
            }
        }
        return apiManager;
    }

    /**
     * 模拟拼接http请求的头文件
     */
//    private Interceptor encodeInterceptor = new Interceptor() {
//        @Override
//        public Response intercept(Chain chain) throws IOException {
//            Request requestBody = chain.request();
//            RequestBody old = requestBody.body();
//            if (old == null || old.contentType().type().equals("multipart")) {
//                return chain.proceed(chain.request());
//            }
//            ByteArrayOutputStream iaos = new ByteArrayOutputStream();
//            BufferedSink sink = Okio.buffer(Okio.sink(iaos));
//            old.writeTo(sink);
//            sink.flush();
//            byte[] bs = iaos.toByteArray();
//            String value = new String(bs, "utf-8");
//            try {
//                value = EncodeUtil.encode(value);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            String requestElement = GsonUtil.getInstance().toJson(new EncodeData(value));
//            RequestBody newRb = RequestBody.create(old.contentType(), requestElement.getBytes("utf-8"));
//            Request newRequest = requestBody.newBuilder().url(requestBody.url()).header("Content-Type", requestBody.headers().get("Content-Type")).header("Content-Length", "" + requestElement.getBytes().length).post(newRb).build();
//            Response response = chain.proceed(newRequest);
//            return response;
//        }
//    };

    /**
     * 给http头文件添加cookie缓存
     */
//    private Interceptor addCookiesInterceptor = new Interceptor() {
//        @Override
//        public Response intercept(Chain chain) throws IOException {
//            Request.Builder builder = chain.request().newBuilder();
//            HashSet<String> preferences = (HashSet) SharePresUtil.getStringSet(SharePresUtil.KEY_COOKIE);
//            if (preferences != null) {
//                for (String cookie : preferences) {
//                    builder.addHeader("Cookie", cookie);
//                }
//            }
//            return chain.proceed(builder.build());
//        }
//    };

    /**
     * 给http设置头文件cookie缓存
     */
//    private Interceptor receivedCookiesInterceptor = new Interceptor() {
//        @Override
//        public Response intercept(Chain chain) throws IOException {
//            Response originalResponse = chain.proceed(chain.request());
//            if (!originalResponse.headers("Set-Cookie").isEmpty()) {
//                HashSet<String> cookies = new HashSet<>();
//                for (String header : originalResponse.headers("Set-Cookie")) {
//                    cookies.add(header);
//                }
//                SharePresUtil.putStringSet(SharePresUtil.KEY_COOKIE, cookies);
//            }
//            return originalResponse;
//        }
//    };

    public static UserService getUserService() {
        return getInstance()._getUserService();
    }


    public UserService _getUserService() {
        return retrofit.create(UserService.class);
    }
}
