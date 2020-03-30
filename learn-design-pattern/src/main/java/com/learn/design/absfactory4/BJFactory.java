package com.learn.design.absfactory4;

/**
 * @author xrb
 * @create 2020-03-30 21:51
 */
public class BJFactory implements AbsFactory {

    @Override
    public Pizza createPizza(String orderType) {
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
