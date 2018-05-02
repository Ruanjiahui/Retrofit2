package ruan.com.retrofit2;

import com.google.gson.Gson;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by 19820 on 2018/4/30.
 */

@Getter
@Setter
public class BugUploadBean {

    private String bround;

    private String model;

    private String bugData;

    private String debugOS;

    private String debugOSVersion;

    /**
     * app包名
     */
    private String appPackage;
    /**
     * app版本名称
     */
    private String appVersionName;

    /**
     * app版本号
     */
    private int appVersionCode;
    /**
     * app安装日期
     */
    private String appInstallDate;
    /**
     * app 更新日期
     */
    private String appInstallUpdateDate;

    /**
     * 安装类型
     */
    private String phoneType = "phone";

    /**
     * 经度
     */
    private double debugLon;

    /**
     * 纬度
     */
    private double debugLat;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
