package ruan.com.retrofit2.BaseControl;

import android.app.Activity;

import java.util.ArrayList;

import ruan.com.retrofit2.Log.LogFactory;

/**
 * Created by 19820 on 2018/4/18.
 */

public class ActivityFactory{

    private static ArrayList<Object> map;

    private static ActivityFactory factory;

    private static LogFactory LOG = LogFactory.getInstance();

    public static ActivityFactory getInstance(){
        if (map == null) {
            map = new ArrayList<>();
            factory = new ActivityFactory();
        }
        return factory;
    }


    /**
     * 添加activity 到链表里面
     * @param object
     */
    public void add(Object object){
        if (map != null) {
            map.add(object);
            LOG.i(ActivityFactory.class , "add:" + map.toString());
        }
    }

    /**
     * 移除activity
     * @param object
     */
    public void remove(Object object){
        if (map != null && map.size() > 0) {
            boolean isRM = false;
            LOG.i(ActivityFactory.class , "remove:" + map.toString());
            for (Object obj : map) {
                //如果 移除的对象 存在  则移除  对象后面的全部移除
                if (object == obj || isRM) {
                    map.remove(obj);
                    isRM = true;
                    ((Activity) obj).finish();
                }
            }
        }
    }

    /**
     * 移除全部的对象那个
     */
    public void removeAll(){
        if (map != null && map.size() > 0) {
            for (int i = map.size() - 1; i >= 0; i--) {
                Object obj = map.get(i);
                ((Activity) obj).finish();
            }
        }
    }




}
