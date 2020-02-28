package com.learn.item5;

import java.util.Date;

/**
 * @author xrb
 * @create 2020-01-26 21:13
 * 局部变量表 字节码文件测试
 */
public class LocalVariablesTest {
    //静态方法
    public static void main(String[] args) {
        LocalVariablesTest test = new LocalVariablesTest();
        int a = 10;
        double b = 20.3;
        Date date = new Date();
    }

    //构造器
    public LocalVariablesTest(){
        int a = 10;
        double b = 20.3;
        Date date = new Date();
    }

    //普通实例方法
    public void test(){
        double b = 20.3;
    }
}
