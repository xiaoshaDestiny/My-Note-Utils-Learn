package com.learn.design.simplefactory2.pizzacase;

/**
 * @author xrb
 * @create 2020-03-30 16:46
 * 奶酪披萨
 */
public class CheesePizza extends Pizza {
    @Override
    public void prepare() {
        System.out.println("准备制作奶酪披萨的材料。。。");
    }
}
