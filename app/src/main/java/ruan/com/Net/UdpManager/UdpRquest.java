package ruan.com.Net.UdpManager;

import android.content.Context;
import android.os.Looper;

import java.util.Timer;
import java.util.TimerTask;

import ruan.com.Net.BaseNetCallback;
import ruan.com.Net.UdpManager.Interface.UdpCallback;

import static ruan.com.Net.UdpManager.Settings.*;

/**
 * UDP 发送和 接口的实体类
 * Created by 19820 on 2018/3/25.
 */

public class UdpRquest extends BaseRequest {

    private UdpCallback.Response callback;
    //发送次数  默认发送一次
    private int count = Settings.count;
    //定时器
    private Timer timer;
    //统计次数
    private int total = 0;

    public UdpRquest(Context context, UdpCallback.Response callback, BaseNetCallback netCallback, int count) {
        super(context, netCallback);
        this.callback = callback;
        this.count = count;
        this.timer = new Timer();
    }

    public UdpRquest(Context context, UdpCallback.Response callback, BaseNetCallback netCallback) {
        super(context, netCallback);
        this.callback = callback;
        this.timer = new Timer();
    }

    public UdpRquest(Context context, UdpCallback.Response callback, int count) {
        super(context, null);
        this.callback = callback;
        this.count = count;
        this.timer = new Timer();
    }

    public UdpRquest(Context context, UdpCallback.Response callback) {
        super(context, null);
        this.callback = callback;
        this.timer = new Timer();
    }

    /**
     * 发送数据
     *
     * @param msg         发送数据
     * @param netAddress  发送地址
     * @param PORT        发送端口
     * @param requestCode 发送标识
     */
    public void request(final byte[] msg, final String netAddress, final int PORT, final int requestCode) {
        //默认接受数据长度为1K
        //开启子线程接收数据
        byte[] receBuf = new byte[1024];
        reviced(receBuf, 0, new ObservableCallback() {
            @Override
            public void onSuccess(int requestCode, String msg) {
                //当接收到数据的时候马上停止定时器
                timer.cancel();
                callback.onUdpSuccess(requestCode, msg);
            }

            @Override
            public void onError(int requestCode, Throwable throwable) {
                callback.onUdpError(requestCode, throwable);
            }
        }, requestCode);

        if (timer != null) {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Looper.prepare();
                    isOut();
                    //发送数据
                    send(msg, netAddress, PORT);
                    Looper.loop();
                }
            }, depay_time_out, time_out);
        }
    }

    /**
     * 发送数据
     *
     * @param msg         发送数据
     * @param netAddress  发送地址
     * @param PORT        发送端口
     * @param requestCode 发送标识
     * @param isReviced   是否接收消息
     */
    public void request(final byte[] msg, final String netAddress, final int PORT, final int requestCode, boolean isReviced) {

        //设置是否接收消息
        if (isReviced) {
            //默认接受数据长度为1K
            //开启子线程接收数据
            byte[] receBuf = new byte[1024];
            reviced(receBuf, 0, new ObservableCallback() {
                @Override
                public void onSuccess(int requestCode, String msg) {
                    //当接收到数据的时候马上停止定时器
                    timer.cancel();
                    callback.onUdpSuccess(requestCode, msg);
                }

                @Override
                public void onError(int requestCode, Throwable throwable) {
                    callback.onUdpError(requestCode, throwable);
                }
            }, requestCode);
        }
        if (timer != null) {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Looper.prepare();
                    isOut();
                    //发送数据
                    send(msg, netAddress, PORT);
                    Looper.loop();
                }
            }, depay_time_out, time_out);
        }
    }

    /**
     * 接收数据的方法
     *
     * @param receBuf     接收数据的数组
     * @param PORT        监控端口
     * @param requestCode 接收标识
     */
    public void reviced(byte[] receBuf, int PORT, int requestCode) {
        this.reviced(receBuf, PORT, new ObservableCallback() {
            @Override
            public void onSuccess(int requestCode, String msg) {
                callback.onUdpSuccess(requestCode, msg);
            }

            @Override
            public void onError(int requestCode, Throwable throwable) {
                callback.onUdpError(requestCode, throwable);
            }
        }, requestCode);
    }

    /**
     * 判断是否超过指定次数
     *
     * @return
     */
    private boolean isOut() {
        total++;
        if (total <= count)
            return false;

        //如果超过规定的次数则取消定时器
        timer.cancel();
        return true;
    }
}
