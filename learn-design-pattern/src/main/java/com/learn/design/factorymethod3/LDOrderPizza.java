package com.learn.design.factorymethod3;


/**
 * @create 2020-03-30 21:09
 */
public class LDOrderPizza extends OrderPizza {

    public LDOrderPizza(String orderType) {
        super(orderType);
    }

    @Override
    Pizza createPizza(String orderType) {
        Pizza pizza = null;
        if(orderType.equals("greek")){
            pizza = new LDGreekPizza();
        }
        return pizza;
    }
}
