package ruan.com.Net;

/**
 * Created by 19820 on 2018/1/8.
 */

public interface BaseNetCallback {

    /**
     * 断网
     */
    void noConnect();

    /**
     * 请求失败
     */
    void onFail();

    /**
     * 请求完成
     */
    void onCompleted();
}
