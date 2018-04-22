package ruan.com.retrofit2.ServiceControl;

import java.util.ArrayList;

/**
 * Created by 19820 on 2018/4/22.
 */

public class ServiceFactory {

    private static ArrayList<Object> serviceList;

    private static ServiceFactory factory;

    public static ServiceFactory getInstance(){
        if (serviceList == null){
            serviceList = new ArrayList<>();
            factory = new ServiceFactory();
        }
        return factory;
    }

    /**
     * 添加服务对象到 储存链表里面
     * @param object
     */
    public void addService(Object object){
        if (serviceList != null)
            serviceList.add(object);
    }

    /**
     * 移除 服务对象
     * @param object
     */
    public void removeService(Object object){
        if (serviceList != null && serviceList.size() > 0)
            serviceList.remove(object);
    }

    /**
     * 移除所有服务
     */
    public void removeAll(){
        if (serviceList != null && serviceList.size() > 0.)
            serviceList.removeAll(serviceList);
    }

}
