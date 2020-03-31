package com.learn.design.builder6.use;

/**
 * @author xrb
 * @create 2020-03-31 21:40
 */
public class CommonHouse extends HouseBuilder {
    @Override
    public void buildBasic() {
        System.out.println("建造普通房子的 地基");
    }

    @Override
    public void buildWalls() {
        System.out.println("建造普通房子的 墙");
    }

    @Override
    public void roofed() {
        System.out.println("建造普通房子的 屋顶");
    }
}
