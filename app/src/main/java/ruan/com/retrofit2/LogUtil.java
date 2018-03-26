package ruan.com.retrofit2;

import android.util.Log;


/**
 * Created by tanxinye on 2016/10/22.
 */
public class LogUtil {

    public static void e(String tag, String mes) {
        if (!Config.isDebug) {
            return;
        }
        if (tag == null || mes == null) {
            e(LogUtil.class.getSimpleName(), "tag or mes null");
        } else {
            Log.e(tag, mes);
        }
    }

    public static void e(String mes) {
        if (mes == null) {
            e(LogUtil.class.getSimpleName(), "mes null");
        } else {
            e("xiaohui", mes);
        }
    }

    public static void e(Exception value) {
        if (!Config.isDebug) {
            return;
        }
        if(value != null) {
            Log.e(LogUtil.class.getSimpleName(), value.getMessage());
        }
    }
}
