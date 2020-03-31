package com.learn.design.builder6.nouse;

/**
 * @author xrb
 * @create 2020-03-31 21:18
 */
public abstract class AbstractHouse {
    //打地基
    public abstract void builderBasic();

    //砌墙
    public abstract void buildWalls();

    //封顶
    public abstract void roofed();

    public void build(){
        builderBasic();
        buildWalls();
        roofed();
    }
}
