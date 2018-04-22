package ruan.com.retrofit2.ServiceControl;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import ruan.com.retrofit2.Log.LogFactory;
import ruan.com.retrofit2.MainActivity;
import ruan.com.retrofit2.R;

/**
 * Created by 19820 on 2018/4/22.
 */

public class BaseService extends Service {

    private static LogFactory LOG = LogFactory.getInstance();

    @Override
    public void onCreate() {
        super.onCreate();
        LOG.i(BaseService.class, "onCreate");
        //添加服务到链表里面
        ServiceFactory.getInstance().addService(this);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LOG.i(BaseService.class, "onStartCommand");

        showNotification();

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LOG.i(BaseService.class, "onBind");
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LOG.i(BaseService.class, "onDestroy");
        //移除服务
        ServiceFactory.getInstance().removeService(this);
    }


    /**
     * 启动前台通知
     */
    private void showNotification() {
        //创建通知详细信息
        Notification.Builder mBuilder = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("2016年11月24日")
                .setContentText("今天天气阴天，8到14度");

        //创建点击跳转Intent
        Intent intent = new Intent(this, MainActivity.class);
        //创建任务栈Builder
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        //设置跳转Intent到通知中
        mBuilder.setContentIntent(pendingIntent);
        //获取通知服务
        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //构建通知
        Notification notification = mBuilder.build();
        //通知栏不可清除
        notification.flags = Notification.FLAG_NO_CLEAR;
        //显示通知
        nm.notify(0, notification);
        //启动为前台服务
        startForeground(0, notification);
    }
}
