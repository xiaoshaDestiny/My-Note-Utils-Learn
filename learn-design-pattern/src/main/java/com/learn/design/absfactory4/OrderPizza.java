package com.learn.design.absfactory4;

/**
 * @author xrb
 * @create 2020-03-30 21:55
 */
public class OrderPizza {
    private AbsFactory factory;
    private Pizza pizza;

    public OrderPizza(AbsFactory factory,String orderType) {
        this.factory = factory;
        Pizza pizza = factory.createPizza(orderType);
        if(pizza != null){
            this.pizza = pizza;
            this.pizza.prepare();
            this.pizza.bake();
            this.pizza.cut();
            this.pizza.box();
        }else {
            System.out.println("创建失败");
        }

    }

    public Pizza getPizza() {
        return pizza;
    }
}
