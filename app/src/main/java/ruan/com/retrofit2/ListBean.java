package ruan.com.retrofit2;

import java.util.List;

/**
 * Created by 19820 on 2018/1/6.
 */

public class ListBean {

    /**
     * id : 157
     * uid : 30
     * picUrl : []
     * title : 什么样的误解才算是“重大误解”？
     * content : http://39.108.144.9:8855/LawHtml/1513049705206.html
     * readCount : 0
     * difTime : 25天前
     * time : 2017-12-12
     * shareCount : 0
     * address : http://39.108.144.9:8855/LawHtml/
     * original : 0
     * subContent : 我国《民法总则》第一百四十七条规定：基于重大误解实施的民事法律行为，行为人有权请求人民法院或者仲裁机构予以撤销。《合同法》第五十四条规定：因重大误解订立的合同，当事人一方有权请求人民法院或者仲裁机构变
     * originalNumber :
     * message : success
     * code : 200
     */

    private int id;
    private int uid;
    private String title;
    private String content;
    private int readCount;
    private String difTime;
    private String time;
    private int shareCount;
    private String address;
    private int original;
    private String subContent;
    private String originalNumber;
    private String message;
    private int code;
    private List<?> picUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getReadCount() {
        return readCount;
    }

    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }

    public String getDifTime() {
        return difTime;
    }

    public void setDifTime(String difTime) {
        this.difTime = difTime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getShareCount() {
        return shareCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getOriginal() {
        return original;
    }

    public void setOriginal(int original) {
        this.original = original;
    }

    public String getSubContent() {
        return subContent;
    }

    public void setSubContent(String subContent) {
        this.subContent = subContent;
    }

    public String getOriginalNumber() {
        return originalNumber;
    }

    public void setOriginalNumber(String originalNumber) {
        this.originalNumber = originalNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<?> getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(List<?> picUrl) {
        this.picUrl = picUrl;
    }

    @Override
    public String toString() {
        return "ListBean{" +
                "id=" + id +
                ", uid=" + uid +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", readCount=" + readCount +
                ", difTime='" + difTime + '\'' +
                ", time='" + time + '\'' +
                ", shareCount=" + shareCount +
                ", address='" + address + '\'' +
                ", original=" + original +
                ", subContent='" + subContent + '\'' +
                ", originalNumber='" + originalNumber + '\'' +
                ", message='" + message + '\'' +
                ", code=" + code +
                ", picUrl=" + picUrl +
                '}';
    }
}
