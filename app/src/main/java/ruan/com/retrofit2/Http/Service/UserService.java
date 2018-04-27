package ruan.com.retrofit2.Http.Service;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.QueryName;
import ruan.com.retrofit2.EncodeData;
import rx.Observable;

/**
 * Created by tanxinye on 2016/10/23.
 */
public interface UserService {

//    @POST("user/register/")
//    Observable<EncodeData> registerRequest(@Body RegisterRequest request);
//
//    @POST("user/uploadImage/")
//    Observable<EncodeData> uploadRegisterAvatarRequest(@Body MultipartBody multipartBody, @Query("data") String data);

    @POST("app/gettopicpage.do")
    Observable<EncodeData> gettopicpage(@Query("page") int page , @Query("type") int type);

//    @POST("app/gettopicpage.do")
//    Call<EncodeData> gettopicpage(@Body RegisterRequest request);
}
