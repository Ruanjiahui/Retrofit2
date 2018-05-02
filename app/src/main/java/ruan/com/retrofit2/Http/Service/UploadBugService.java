package ruan.com.retrofit2.Http.Service;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;
import ruan.com.retrofit2.BugUploadBean;
import ruan.com.retrofit2.EncodeData;
import ruan.com.retrofit2.HttpBaseResp;
import rx.Observable;

/**
 * Created by 19820 on 2018/4/30.
 */
public interface UploadBugService {

    @POST("BugCommit")
    Observable<HttpBaseResp> commitBug(@Body BugUploadBean bugUploadBean);

}
