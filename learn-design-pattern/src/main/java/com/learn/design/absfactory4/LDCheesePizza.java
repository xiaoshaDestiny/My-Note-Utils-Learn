package com.learn.design.absfactory4;

/**
 * @author xrb
 * @create 2020-03-30 21:47
 */
public class LDCheesePizza extends Pizza {
    @Override
    public void prepare() {
        setName("伦敦奶酪披萨");
        System.out.println("准备伦敦奶酪披萨的材料");
    }
}
