package com.learn.design.factorymethod3;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author xrb
 * @create 2020-03-30 16:49
 * 订购披萨
 */
public abstract class OrderPizza {

    //定义抽象方法
    abstract Pizza createPizza(String orderType);

    public OrderPizza(String orderType) {
        Pizza pizza = null;
            pizza = createPizza(orderType);
            if(pizza!=null){
                pizza.prepare();
                pizza.bake();
                pizza.cut();
                pizza.box();
            }else {
                System.out.println("订购失败");
            }

     /*   Pizza pizza = null;
        do {
            pizza = createPizza(getType());
            if(pizza!=null){
                pizza.prepare();
                pizza.bake();
                pizza.cut();
                pizza.box();
            }else {
                System.out.println("订购失败");
                break;
            }
        }while (true);*/


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
