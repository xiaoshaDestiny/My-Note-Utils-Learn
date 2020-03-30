package com.learn.design.factorymethod3;

/**
 * @author xrb
 * @create 2020-03-30 21:12
 */
public class PizzaStore {
    public static void main(String[] args) {
        new BJOrderPizza("greek");
        new BJOrderPizza("cheese");
        new LDOrderPizza("greek");
    }
}
