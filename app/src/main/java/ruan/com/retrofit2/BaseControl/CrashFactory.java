package ruan.com.retrofit2.BaseControl;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import ruan.com.Net.HttpManager.HttpResponse;
import ruan.com.Net.HttpManager.Interface.HttpCallback;
import ruan.com.Utils.AppApplicationMgr;
import ruan.com.Utils.AppDateMgr;
import ruan.com.Utils.AppPhoneMgr;
import ruan.com.retrofit2.BugUploadBean;
import ruan.com.retrofit2.Http.RequestApi;
import ruan.com.retrofit2.HttpBaseResp;
import ruan.com.retrofit2.Log.LogFactory;

/**
 * Created by 19820 on 2018/4/19.
 */

public class CrashFactory implements Thread.UncaughtExceptionHandler {

    public static final String TAG = "CrashHandler";

    // 系统默认的UncaughtException处理类
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    // CrashHandler实例
    private static CrashFactory INSTANCE = new CrashFactory();
    // 程序的Context对象
    private Context mContext;
    // 用来存储设备信息和异常信息
    private Map<String, String> infos = new HashMap<String, String>();

    // 用于格式化日期,作为日志文件名的一部分
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //收集错误日志
    private String error;

    private CrashFactory() {
    }

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static CrashFactory getInstance() {
        return INSTANCE;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        mContext = context;
        // 获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            //将数据上传

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Log.e(TAG, "error : ", e);
            }
            //移除所有activity
            ActivityFactory.getInstance().removeAll();
            // 退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        // 使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "很抱歉,程序出现异常即将退出.", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();
        // 收集设备参数信息
        collectDeviceInfo(mContext);
        // 保存日志文件
        saveCrashInfo2File(ex);

        //将数据上传到服务器
        BugUploadBean bugUploadBean = new BugUploadBean();
        AppPhoneMgr appPhoneMgr = new AppPhoneMgr();
        bugUploadBean.setModel(appPhoneMgr.getPhoneModel());
        bugUploadBean.setDebugOSVersion(AppPhoneMgr.getSystemVersion() + " " + appPhoneMgr.getSDKVersionNumber());
        if (appPhoneMgr.isTablet(mContext))
            bugUploadBean.setPhoneType("pad");
        bugUploadBean.setAppPackage(AppApplicationMgr.getPackageName(mContext));
        bugUploadBean.setAppInstallDate(formatter.format(new Date(AppApplicationMgr.getAppFirstInstallTime(mContext , bugUploadBean.getAppPackage()))));
        bugUploadBean.setAppInstallUpdateDate(formatter.format(new Date(AppApplicationMgr.getAppLastUpdateTime(mContext , bugUploadBean.getAppPackage()))));
        bugUploadBean.setAppVersionName(AppApplicationMgr.getVersionName(mContext));
        bugUploadBean.setAppVersionCode(AppApplicationMgr.getVersionCode(mContext));
        bugUploadBean.setBugData(error);
        bugUploadBean.setBround(AppPhoneMgr.getDeviceBrand());
        bugUploadBean.setDebugOS(AppPhoneMgr.getDeviceSystem());

        new RequestApi(mContext).UploadBug(bugUploadBean, new HttpCallback.Response() {

            @Override
            public void onSuccess(int requestCode, HttpResponse response) {
                HttpBaseResp resp = (HttpBaseResp) response;
                LogFactory.getInstance(CrashFactory.class).i("UploadBug" , resp.toString());
                if (resp.getCode() == 200000){
                    System.out.println("bug 提交成功");
                }
            }

            @Override
            public void onError(int requestCode, Throwable throwable) {

            }
        } , 200);

        return true;
    }

    /**
     * 收集设备参数信息
     *
     * @param ctx
     */
    public void collectDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "an error occured when collect package info", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
                Log.d(TAG, field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                Log.e(TAG, "an error occured when collect crash info", e);
            }
        }
    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return 返回文件名称, 便于将文件传送到服务器  路径返回绝对路径
     */
    private String saveCrashInfo2File(Throwable ex) {

        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        try {
            error = sb.toString();

            String time = formatter.format(new Date());
            String fileName = time + ".txt";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String path = Environment.getExternalStorageDirectory().getPath() + "/Android/Crash/";
                File dir = new File(path);
                if (!dir.exists()) {
                    boolean w = dir.mkdirs();
                    System.out.println("mkdir:" + w);
                }
                FileOutputStream fos = new FileOutputStream(path + fileName);
                fos.write(sb.toString().getBytes("UTF-8"));
                fos.close();
                return path + fileName;
            } else
                return null;
        } catch (Exception e) {
            Log.e(TAG, "an error occured while writing file...", e);
        }
        return null;
    }
}
