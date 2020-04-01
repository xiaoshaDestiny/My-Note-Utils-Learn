package com.learn.design.adapter7.objectadapter;


/**
 * @author xrb
 * @create 2020-04-01 20:59
 *
 * 对象适配器类
 */
public class VoltageAdapter implements Voltage5V {
    private Voltage220V voltage220V;

    public VoltageAdapter(Voltage220V voltage220V) {
        this.voltage220V = voltage220V;
    }

    @Override
    public int output5V() {
        //获取到220v电压
        int srcV = voltage220V.output220V();
        int dstV = srcV / 44;
        System.out.println("使用对象适配器  标准电压转换成手机电压成功....");
        return dstV;
    }
}
