package com.learn.design.factorymethod3;

/**
 * @author xrb
 * @create 2020-03-30 11:24
 * 披萨类，做成一个抽象类
 */
public abstract class Pizza {
    protected String name;//披萨的名字

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 准备原材料
     */
    public abstract void prepare();


    public void bake(){
        System.out.println(name + "烘烤");
    }

    public void cut(){
        System.out.println(name + "切割");
    }

    public void box(){
        System.out.println(name + "打包");
    }
}
