package com.learn.design.absfactory4;

import com.learn.design.simplefactory2.pizzacase.CheesePizza;

/**
 * @author xrb
 * @create 2020-03-30 21:53
 */
public class LDFactory implements AbsFactory {
    @Override
    public Pizza createPizza(String orderType) {
        Pizza pizza = null;
        if(orderType.equals("cheese")){
            pizza = new LDCheesePizza();
        }
        if(orderType.equals("greek")){
            pizza = new LDGreekPizza();
        }
        return pizza;
    }
}
