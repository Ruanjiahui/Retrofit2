package ruan.com.Net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by tanxinye on 2016/10/23.
 */
public class NetWorkUtil {

    private static Context context;

    /**
     * 判断是否有网络
     * @param context
     * @return
     */
    public static boolean isNetWorkAvailable(Context context) {
        NetWorkUtil.context = context;
        NetworkInfo networkInfo = getNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    /**
     * 判断是否有连接wifi
     * @param context
     * @return
     */
    public static boolean isWifiConnected(Context context) {
        NetWorkUtil.context = context;
        NetworkInfo networkInfo = getNetworkInfo();
        return networkInfo != null && isNetWorkAvailable(context) && networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

    private static NetworkInfo getNetworkInfo() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }
}
