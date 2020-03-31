package com.learn.design.builder6.nouse;

/**
 * @author xrb
 * @create 2020-03-31 21:21
 */
public class CommonHouse extends AbstractHouse{

    @Override
    public void builderBasic() {
        System.out.println("给普通房子打地基");
    }

    @Override
    public void buildWalls() {
        System.out.println("给普通房子 砌墙");
    }

    @Override
    public void roofed() {
        System.out.println("给普通房子 盖屋顶");
    }
}
