package com.learn.design.simplefactory2.pizzacase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author xrb
 * @create 2020-03-30 16:49
 * 订购披萨
 */
public class OrderPizza {

  /*  public OrderPizza() {
        Pizza pizza = null;
        String orderType;
        do{
            orderType = getType();
            if(orderType.equals("greek")){
                pizza = new GreekPizza();
                pizza.setName("希腊披萨");
            }else if(orderType.equals("cheese")){
                pizza = new CheesePizza();
                pizza.setName("奶酪披萨");
            }else{
                break;
            }
            pizza.prepare();
            pizza.bake();
            pizza.cut();
            pizza.box();

        }while (true);
    }*/

    SimplePizzaFactory simplePizzaFactory;
    Pizza pizza = null;

    public OrderPizza(SimplePizzaFactory simplePizzaFactory) {
        setSimplePizzaFactory(simplePizzaFactory);
    }

    public void setSimplePizzaFactory(SimplePizzaFactory simplePizzaFactory){
        String orderType = "";
        this.simplePizzaFactory = simplePizzaFactory;

        do {
            orderType = getType();
            pizza = this.simplePizzaFactory.createPizza(orderType);

            if(pizza!=null){
                pizza.prepare();
                pizza.bake();
                pizza.cut();
                pizza.box();
            }else {
                System.out.println("订购失败");
                break;
            }
        }while (true);
    }



    private String getType(){

        try {
            BufferedReader strIn = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("input pizza type :");
            return strIn.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

    }
}
