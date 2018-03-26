package ruan.com.retrofit2;


/**
 * Created by Ruanjiahui on 2017/4/28.
 */

public class RegisterPresenter extends BasePresenter implements RegisterCallback.RequestCallback {

    private RegisterCallback.ResponseCallback callback;

//    public RegisterPresenter(RegisterCallback.ResponseCallback callback) {
//        super(callback);
//        this.callback = callback;
//    }

    @Override
    public void requestRegister(RegisterRequest request) {
//        execute(ApiManager.getInstance().getUserService().gettopicpage(request.getPage()), TokenResponse.class, new ObservableCallback<TokenResponse>() {
//
//            @Override
//            public void onSuccess(TokenResponse tokenResponse) {
//                if(tokenResponse.getCode()== 200){
//                    callback.responseRegister(tokenResponse);
//                }else {
//                    callback.responseFail();
//                }
//            }
//
//        });
    }
}
