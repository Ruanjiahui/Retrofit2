package ruan.com.retrofit2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.Arrays;
import java.util.List;

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


        if (checkPermission(new String[]{Manifest.permission.CALL_PHONE} , 200)){
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:15119481373"));
            Toast.makeText(this, "123", Toast.LENGTH_SHORT).show();
            startActivity(intent);
            List<String> list = null;
            System.out.println(list.toString());
        }
    }

}
