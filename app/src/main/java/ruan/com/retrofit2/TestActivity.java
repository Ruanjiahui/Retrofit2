package ruan.com.retrofit2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import ruan.com.retrofit2.BaseControl.BaseActivity;

/**
 * Created by Administrator on 2018/4/11.
 */

public class TestActivity extends BaseActivity {


    /**
     * activity  start
     */
    @Override
    protected void init() {


//        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ListBean listBean = new ListBean();
//                listBean.setAddress("测试回调数据");
//                EventBus.getDefault().post(listBean);
//
//                finish();
//            }
//        });
//        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                EventBus.getDefault().post("测试回调数据");
//
//                finish();
//            }
//        });
    }

    @Override
    public void OnClickCenterText() {
        super.OnClickCenterText();

        requestPermission(new String[]{Manifest.permission.CALL_PHONE}, 0);
    }

    @Override
    public void permissionFail(int requestCode) {
        super.permissionFail(requestCode);

        showTipsDialog();
    }

    @SuppressLint("MissingPermission")
    @Override
    public void permissionSuccess(int requestCode) {
        super.permissionSuccess(requestCode);
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:15119481373"));
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            startActivity(intent);
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
//        }
//        ActivityCompat.requestPermissions(this , new String[]{Manifest.permission.CALL_PHONE} , 0);
//        Toast.makeText(TestActivity.this, "权限开启成功", Toast.LENGTH_LONG).show();
    }



    //    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.test_main);
//
//
//
//    }
}
