package com.learn.design.factorymethod3;


/**
 * @author xrb
 * @create 2020-03-30 21:03
 */
public class BJOrderPizza extends OrderPizza{

    public BJOrderPizza(String orderType) {
        super(orderType);
    }

    @Override
    Pizza createPizza(String orderType) {
        Pizza pizza = null;
        if(orderType.equals("cheese")){
            pizza = new BJCheesePizza();
        }
        if(orderType.equals("greek")){
            pizza = new BJGreekPizza();
        }
        return pizza;
    }
}
