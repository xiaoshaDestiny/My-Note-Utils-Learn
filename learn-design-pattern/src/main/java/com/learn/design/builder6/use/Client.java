package com.learn.design.builder6.use;

/**
 * @author xrb
 * @create 2020-03-31 21:47
 */
public class Client {
    public static void main(String[] args) {
        //盖房子
        CommonHouse commonHouse = new CommonHouse();

        //创建指挥者
        HouseDirector houseDirector = new HouseDirector(commonHouse);

        //指挥者创建房子
        House house = houseDirector.constructHouse();
    }
}
