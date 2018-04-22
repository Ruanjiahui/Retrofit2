package ruan.com.retrofit2.Log;

import android.util.Log;

import ruan.com.retrofit2.Constant;

/**
 * 打印日志
 * Created by 19820 on 2018/4/22.
 */
public class LogFactory {

    private static LogFactory factory;

    public static LogFactory getInstance() {
        if (factory == null)
            factory = new LogFactory();
        return factory;
    }


    /**
     * 打印日志
     *
     * @param cla 类名
     * @param msg 信息
     */
    public void i(Class<?> cla, String msg) {
        if (Constant.isDebug)
            Log.i(cla.getSimpleName(), msg);
    }

}
