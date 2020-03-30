package com.learn.design.simplefactory2.pizzacase;

/**
 * @author xrb
 * @create 2020-03-30 17:09
 * 简单工厂模式
 */
public class SimplePizzaFactory {

    public Pizza createPizza(String orderType){
        System.out.println("使用简单工厂模式......");

        Pizza pizza = null;

        if(orderType.equals("greek")){
            pizza = new GreekPizza();
            pizza.setName("希腊披萨");
        }

        if(orderType.equals("cheese")){
            pizza = new GreekPizza();
            pizza.setName("奶酪披萨");
        }

        if(orderType.equals("pepper")){
            pizza = new GreekPizza();
            pizza.setName("胡椒披萨");
        }

        return pizza;
    }
}
