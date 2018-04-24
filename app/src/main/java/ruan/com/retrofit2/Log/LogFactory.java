package ruan.com.retrofit2.Log;

import android.util.Log;

import ruan.com.retrofit2.Constant;

/**
 * 打印日志
 * Created by 19820 on 2018/4/22.
 */
public class LogFactory {

    private static LogFactory factory;
    private static String ClassName;

    public static LogFactory getInstance(Class<?> cla) {
        if (factory == null) {
            factory = new LogFactory();
        }
        ClassName = cla.getSimpleName();
        return factory;
    }


    /**
     * 打印日志
     *
     * @param title  标题
     * @param msg 信息
     */
    public void i(String title, String msg) {
        if (Constant.isDebug)
            Log.i(ClassName + ":" + title + "-------->", msg);
    }


    /**
     * 打印日志
     *
     * @param title  标题
     * @param msg 信息
     */
    public void i(String title, Long msg) {
        if (Constant.isDebug)
            Log.i(ClassName + ":" + title + "-------->", "" + msg);
    }

}
