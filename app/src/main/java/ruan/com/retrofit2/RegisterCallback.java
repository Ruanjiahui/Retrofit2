package ruan.com.retrofit2;


/**
 * Created by Ruanjiahui on 2017/4/28.
 */

public interface RegisterCallback {

    interface RequestCallback extends BaseRequestCallback {
        void requestRegister(RegisterRequest request);

    }

    interface ResponseCallback extends BaseResponseCallback {

        void responseRegister(TokenResponse response);

        void responseFail();
    }
}
