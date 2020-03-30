package com.learn.design.simplefactory2.pizzacase;

/**
 * @author xrb
 * @create 2020-03-30 16:48
 * 希腊披萨
 */
public class GreekPizza extends Pizza{
    @Override
    public void prepare() {
        System.out.println("准备制作希腊披萨的材料。。。");
    }
}
