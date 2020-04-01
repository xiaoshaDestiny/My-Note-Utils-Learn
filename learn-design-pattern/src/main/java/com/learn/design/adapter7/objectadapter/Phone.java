package com.learn.design.adapter7.objectadapter;


/**
 * @author xrb
 * @create 2020-04-01 21:03
 */
public class Phone {

    //充电
    public void cd(Voltage5V voltage5V){
        if(voltage5V.output5V() == 5){
            System.out.println("电压是5V,可以充点");
        }else{
            System.out.println("电压不正常，不可以充点");
        }
    }
}
