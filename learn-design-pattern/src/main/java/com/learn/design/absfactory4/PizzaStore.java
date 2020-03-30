package com.learn.design.absfactory4;

/**
 * @author xrb
 * @create 2020-03-30 22:02
 */
public class PizzaStore {
    public static void main(String[] args) {
        new OrderPizza(new BJFactory(), "greek").getPizza();
        System.out.println("---------");


        new OrderPizza(new BJFactory(), "cheese").getPizza();
        System.out.println("---------");


        new OrderPizza(new LDFactory(), "greek").getPizza();
        System.out.println("---------");


        new OrderPizza(new LDFactory(), "cheese").getPizza();
        System.out.println("---------");

        new OrderPizza(new LDFactory(), "test").getPizza();
    }
}
