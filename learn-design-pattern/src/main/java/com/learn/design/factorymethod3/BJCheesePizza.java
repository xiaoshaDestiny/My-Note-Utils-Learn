package com.learn.design.factorymethod3;

/**
 * @author xrb
 * @create 2020-03-30 20:50
 */
public class BJCheesePizza extends Pizza{

    @Override
    public void prepare() {
        setName("北京奶酪披萨");
        System.out.println("北京的奶酪披萨原料准备");
    }
}
