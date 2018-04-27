package ruan.com.retrofit2.BaseControl;

import android.app.Application;
import android.content.Intent;

import ruan.com.retrofit2.Constant;
import ruan.com.retrofit2.Language.MultiLanguageUtil;
import ruan.com.retrofit2.Log.LogFactory;
import ruan.com.retrofit2.ServiceControl.BaseService;

/**
 * Created by 19820 on 2018/4/19.
 */

public class ApplicationFactory extends Application {

    private static LogFactory LOG = LogFactory.getInstance(ApplicationFactory.class);

    @Override
    public void onCreate() {
        super.onCreate();

        if (!Constant.isDebug)
            //启动错误日志捕捉
            CrashFactory.getInstance().init(this);
        //初始化语言设置
        MultiLanguageUtil.init(this);

        //配置语言
        LOG.i("onCreate" , MultiLanguageUtil.getInstance().getLanguageName(this));
        MultiLanguageUtil.getInstance().updateLanguage(MultiLanguageUtil.getInstance().getLanguageType());


        //启动服务
        Intent intent = new Intent(this , BaseService.class);
        startService(intent);
    }
}
