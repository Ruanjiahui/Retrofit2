package ruan.com.Net.UdpManager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import ruan.com.Net.NetWorkUtil;
import ruan.com.Net.BaseNetCallback;

import static ruan.com.Net.UdpManager.Settings.*;

/**
 * Created by 19820 on 2018/3/25.
 */

public class BaseRequest {

    private Context context;
    private BaseNetCallback netCallback;

    public BaseRequest(Context context, BaseNetCallback netCallback) {
        this.context = context;
        this.netCallback = netCallback;
    }


    protected interface ObservableCallback {

        void onSuccess(int requestCode, String msg);


        void onError(int requestCode, Throwable throwable);

    }

    /**
     * 发送数据
     *
     * @param msg        发送数据
     * @param netAddress 发送地址
     * @param PORT       发送端口
     * @return true 发送成功  false 发送失败
     */
    public void send(byte[] msg, String netAddress, int PORT) {
        //发送前，首先判断wifi是否连接
        if (!NetWorkUtil.isWifiConnected(context)) {
            //如果回调接口不为空则回调回去
            if (netCallback != null)
                netCallback.noConnect();
                //回调接口为空则直接调试网络断开
            else
                Toast.makeText(context, net_error, Toast.LENGTH_SHORT).show();

            return;
        }

        //获取发送数据的对象
        DatagramSocket datagramSocket = UdpManager.getInstance();
        try {
            //创建发送地址
            InetAddress address = InetAddress.getByName(netAddress);
            //创建发送数据对象
            DatagramPacket datagramPacket = new DatagramPacket(msg, msg.length, address, PORT);

            // 发送数据对象为空则直接返回
            if (datagramSocket == null) {
                return;
            }
            datagramSocket.send(datagramPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ObservableCallback revicedcallback;

    /**
     * 接受数据
     *
     * @param receBuf 接受数据的数据
     * @param PORT    接受数据的端口
     */
    public void reviced(final byte[] receBuf, final int PORT, ObservableCallback callback, final int requestCode) {
        //发送前，首先判断wifi是否连接
        if (!NetWorkUtil.isWifiConnected(context)) {
            //如果回调接口不为空则回调回去
            if (netCallback != null)
                netCallback.noConnect();
                //回调接口为空则直接调试网络断开
            else
                Toast.makeText(context, net_error, Toast.LENGTH_SHORT).show();
            return;
        }

        this.revicedcallback = callback;
        new Thread(new Runnable() {
            @Override
            public void run() {
                DatagramSocket datagramSocket = null;
                try {
                    if (PORT == 0)
                        datagramSocket = UdpManager.getInstance();
                    else
                        datagramSocket = new DatagramSocket(PORT);
                    //设置超时时间10秒
                    datagramSocket.setSoTimeout(reviced_time_out);
                    DatagramPacket recePacket = new DatagramPacket(receBuf, receBuf.length);
                    datagramSocket.receive(recePacket);

                    String msg = new String(recePacket.getData(), 0, recePacket.getLength());
                    //接收到数据则关闭
                    datagramSocket.disconnect();
                    datagramSocket.close();

                    Message message = new Message();
                    message.what = 0;
                    message.arg1 = requestCode;
                    message.obj = msg;
                    revicedHandler.sendMessage(message);
                } catch (Exception e) {
                    //如果接收错误则关闭连接
                    if (datagramSocket != null) {
                        datagramSocket.disconnect();
                        datagramSocket.close();
                    }
                    Message message = new Message();
                    message.what = 1;
                    message.arg1 = requestCode;
                    message.obj = e;
                    revicedHandler.sendMessage(message);
                }
            }
        }).start();
    }

    private Handler revicedHandler = new Handler() {

        @Override
        public void dispatchMessage(Message msg) {
            if (revicedcallback != null) {
                int requestCode = msg.arg1;
                switch (msg.what) {
                    //成功
                    case 0:
                        revicedcallback.onSuccess(requestCode, (String) msg.obj);
                        break;
                    //失败
                    case 1:
                        revicedcallback.onError(requestCode, (Throwable) msg.obj);
                        break;
                }
            }
        }
    };

}
