package com.learn.design.factorymethod3;

/**
 * @author xrb
 * @create 2020-03-30 20:53
 */
public class LDGreekPizza extends Pizza{

    @Override
    public void prepare() {
        setName("伦敦胡椒披萨");
        System.out.println("准备伦敦胡椒披萨的材料");
    }
}
