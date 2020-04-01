package com.learn.design.adapter7.classadapter;

/**
 * @author xrb
 * @create 2020-04-01 20:59
 *
 * 适配器类
 */
public class VoltageAdapter extends Voltage220V implements Voltage5V {
    @Override
    public int output5V() {
        //获取到220v电压
        int srcV = output220V();
        int dstV = srcV / 44;
        System.out.println("标准电压转换成手机电压成功....");
        return dstV;
    }
}
