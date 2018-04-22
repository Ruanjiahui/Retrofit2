package ruan.com.retrofit2.BaseControl;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.solver.widgets.ConstraintTableLayout;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;

import java.security.Key;

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
    //设置双击退出
    private boolean isDouble = false;
    //设置退出文字
    private String quit_msg = "";
    //退出按钮触发事件
    private BackKeyListener backKeyListener;


    private ImageView base_title_left_p;
    private ImageView base_title_right_p;

    private TextView base_title_t;
    private TextView base_title_left_t;
    private TextView base_title_right_t;


    private FrameLayout base_con_layout;
    private RelativeLayout base_title_Layout;

    /**
     * 触发点击退出按钮的接口
     */
    public interface BackKeyListener{

        public void onClickBack();

    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_main);

        base_title_left_p = findViewById(R.id.base_title_left_p);
        base_title_right_p = findViewById(R.id.base_title_right_p);
        base_title_t = findViewById(R.id.base_title_t);
        base_title_left_t = findViewById(R.id.base_title_left_t);
        base_title_right_t = findViewById(R.id.base_title_right_t);
        base_con_layout = findViewById(R.id.base_con_layout);
        base_title_Layout = findViewById(R.id.base_title_Layout);

        //创建activity 同时加入链表
        ActivityFactory.getInstance().add(this);

        int colorID = R.color.color_blue;
        base_title_Layout.setBackgroundColor(getResources().getColor(colorID));
        ImmersionBar.with(this).statusBarColor(colorID).fitsSystemWindows(true).init();

        base_title_left_p.setOnClickListener(this);
        base_title_right_p.setOnClickListener(this);
        base_title_t.setOnClickListener(this);
        base_title_left_t.setOnClickListener(this);
        base_title_right_t.setOnClickListener(this);

        init();
    }

    /**
     * 设置标题是否显示
     * @param isVisiable
     */
    public void setTitleVisiable(boolean isVisiable){
        if (!isVisiable) {
            base_title_Layout.setVisibility(View.GONE);
            ImmersionBar.with(this).statusBarColor(R.color.color_transparent , 1).fitsSystemWindows(true).init();
        }
    }

    /**
     * 将布局添加
     * @param layoutID
     */
    public void addContentView(int layoutID){
        base_con_layout.addView(View.inflate(this , layoutID , null));
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
     * 设置双击退出
     * @param isDouble
     * @param quit_msg
     */
    public void setDoubleQuit(boolean isDouble , String quit_msg , BackKeyListener backKeyListener){
        this.isDouble = isDouble;
        this.quit_msg = quit_msg;
        this.backKeyListener = backKeyListener;
    }

    /**
     * 设置双击退出
     * @param isDouble
     * @param quit_msg
     */
    public void setDoubleQuit(boolean isDouble , String quit_msg){
        this.isDouble = isDouble;
        this.quit_msg = quit_msg;
    }

    /**
     * 设置双击退出
     * @param isDouble
     */
    public void setDoubleQuit(boolean isDouble , BackKeyListener backKeyListener){
        this.isDouble = isDouble;
        this.backKeyListener = backKeyListener;
    }

    /**
     * 设置双击退出
     * @param isDouble
     */
    public void setDoubleQuit(boolean isDouble){
        this.isDouble = isDouble;
    }

    //双击退出点击第一次时间
    private long startTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //设置双击退出
        if (isDouble){
            if (keyCode == KeyEvent.KEYCODE_BACK){
                //如果点击时间 与开始 点击时间 大于 2秒 则不是退出 则储存点击时间
                if (System.currentTimeMillis() - startTime > 2000) {
                    startTime = System.currentTimeMillis();
                    //判断文字是否为空 // 空则默认提示
                    if (TextUtils.isEmpty(quit_msg))
                        quit_msg = getResources().getString(R.string.double_quit_t);
                    //提示
                    Toast.makeText(this , quit_msg , Toast.LENGTH_SHORT).show();
                }else {
                    //双击触发事件
                    //触发接口不为空 则返回触发接口
                    if (backKeyListener != null)
                        backKeyListener.onClickBack();
                    //否则 就直接触发默认 退出app
                    else
                        onDestroy();
                }
            }
        } else {
            //点击退出按钮触发方法
            if (keyCode == KeyEvent.KEYCODE_BACK)
                OnClickBackKey();
        }
        return true;
    }

    /**
     * 点击退出按钮
     */
    public void OnClickBackKey(){
        //默认删除activity
        onDestroy();
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
