package ruan.com.Net;

import android.util.Log;

import static ruan.com.Net.Settings.isDebug;

/**
 * Created by Administrator on 2018/4/2.
 */

public class NetLog {


    public static void info(String msg) {
        if (isDebug)
            Log.e("NetLog:", msg);
    }

}
