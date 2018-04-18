package ruan.com.retrofit2.BaseControl;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;

import ruan.com.retrofit2.R;

/**
 * Created by 19820 on 2018/4/18.
 */

public abstract class BaseActivity extends BasePermissions implements View.OnClickListener{

    /**
     * activity  start
     */
    protected abstract void init();

    //标识是否退出
    private boolean isQuit = true;


    private ImageView base_title_left_p;
    private ImageView base_title_right_p;

    private TextView base_title_t;
    private TextView base_title_left_t;
    private TextView base_title_right_t;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_main);

        base_title_left_p = findViewById(R.id.base_title_left_p);
        base_title_right_p = findViewById(R.id.base_title_right_p);
        base_title_t = findViewById(R.id.base_title_t);
        base_title_left_t = findViewById(R.id.base_title_left_t);
        base_title_right_t = findViewById(R.id.base_title_right_t);

        //创建activity 同时加入链表
        ActivityFactory.getInstance().add(this);

        ImmersionBar.with(this).statusBarColor(R.color.color_blue).fitsSystemWindows(true).init();

        base_title_left_p.setOnClickListener(this);
        base_title_right_p.setOnClickListener(this);
        base_title_t.setOnClickListener(this);
        base_title_left_t.setOnClickListener(this);
        base_title_right_t.setOnClickListener(this);

        init();
    }

    /**
     * 点击事件
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //左边图片
            case R.id.base_title_left_p:
                OnClickLeftPic();
                break;
                //左边的文字
            case R.id.base_title_left_t:
                OnClickLeftText();
                break;
                //右边的图片
            case R.id.base_title_right_p:
                OnClickRightPic();
                break;
                //右边的文字
            case R.id.base_title_right_t:
                OnClickRightText();
                break;
                //中间的文字
            case R.id.base_title_t:
                OnClickCenterText();
                break;
        }
    }

    /**
     * 点击 中间的文字
     */
    public void OnClickCenterText(){
    }

    /**
     * 点击 左边的图片
     */
    public void OnClickLeftPic(){
        if (isQuit)
            ActivityFactory.getInstance().remove(this);
    }

    /**
     * 点击 左边的文字
     */
    public void OnClickLeftText(){

    }

    /**
     * 点击 右边的图片
     */
    public void OnClickRightPic(){

    }

    /**
     * 点击 右边的文字
     */
    public void OnClickRightText(){

    }

    /**
     * 设置 是否退出
     */
    public void setQuit(boolean isQuit){
        this.isQuit = isQuit;
    }

    /**
     * 设置左边的图片
     * @param drawable
     */
    public void setLeftImage(Drawable drawable){
        base_title_left_p.setImageDrawable(drawable);
    }

    /**
     * 设置右边的图片
     * @param drawable
     */
    public void setRightImage(Drawable drawable){
        base_title_right_p.setImageDrawable(drawable);
    }

    /**
     * 设置左边的文字
     * @param id
     */
    public void setLeftText(int id){
        base_title_left_t.setText(getResources().getText(id));
    }


    /**
     * 设置右边的文字
     * @param id
     */
    public void setRightText(int id){
        base_title_right_t.setText(getResources().getText(id));
    }

    /**
     * 设置中间的文字
     * @param id
     */
    public void setCenterText(int id){
        base_title_t.setText(getResources().getText(id));
    }

    /**
     * 设置左边的文字
     * @param msg
     */
    public void setLeftText(String msg){
        base_title_left_t.setText(msg);
    }

    /**
     * 设置右边的文字
     * @param msg
     */
    public void setRightText(String msg){
        base_title_right_t.setText(msg);
    }

    /**
     * 设置中间的文字
     * @param msg
     */
    public void setCenterText(String msg){
        base_title_t.setText(msg);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

        //销毁activity 同时移除链表
        ActivityFactory.getInstance().remove(this);
    }
}
