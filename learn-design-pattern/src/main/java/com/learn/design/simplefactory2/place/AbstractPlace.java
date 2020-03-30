package com.learn.design.simplefactory2.place;

/**
 * @author xrb
 * @create 2020-03-30 17:31
 * 景点抽象类
 */
public abstract class AbstractPlace {
    private String placeName;

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public abstract void getTicket();

    public void photo(){
        System.out.println(placeName + "拍照打卡");
    }
}
