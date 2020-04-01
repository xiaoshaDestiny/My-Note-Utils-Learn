package com.learn.design.adapter7.classadapter;

/**
 * @author xrb
 * @create 2020-04-01 20:56
 *
 * 被适配者
 */
public class Voltage220V {

    public int output220V(){
        int v = 220;
        System.out.println("电压是 "+v+" V");
        return v;
    }
}
