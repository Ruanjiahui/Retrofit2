package ruan.com.retrofit2.BaseControl;

import android.app.Application;

/**
 * Created by 19820 on 2018/4/19.
 */

public class ApplicationFactory extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        //启动错误日志捕捉
        CrashFactory.getInstance().init(this);

    }
}
