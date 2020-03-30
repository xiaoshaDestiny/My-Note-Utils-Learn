package com.learn.design.absfactory4;


/**
 * @author xrb
 * @create 2020-03-30 20:52
 */
public class BJGreekPizza extends Pizza {
    @Override
    public void prepare() {
        setName("北京胡椒披萨");
        System.out.println("准备北京胡椒披萨的材料");
    }
}
