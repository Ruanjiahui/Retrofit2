package ruan.com.retrofit2.BaseControl;

import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by 19820 on 2018/4/18.
 */

public class ActivityFactory{

    private static ArrayList<Object> map;

    private static ActivityFactory factory;

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
        map.add(object);
        System.out.println("add:" + map.toString());
    }

    /**
     * 移除activity
     * @param object
     */
    public void remove(Object object){
        boolean isRM = false;
        System.out.println("remove:" + map.toString());
        for (Object obj : map){
            //如果 移除的对象 存在  则移除  对象后面的全部移除
            if (object == obj || isRM){
                map.remove(object);
                isRM = true;
                ((Activity)object).finish();
            }
        }
    }

    /**
     * 移除全部的对象那个
     */
    public void removeAll(){
        for (int i = map.size() - 1 ; i >= 0 ; i--){
            Object obj = map.get(i);
            ((Activity)obj).finish();
        }
    }




}
