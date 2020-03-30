package com.learn.design.simplefactory2.place;

/**
 * @author xrb
 * @create 2020-03-30 17:50
 */
public class UseFactory {
    public static void main(String[] args) {
        HuaShanPlace huashan = (HuaShanPlace) new PlayPlace("华山").getPlace();
        huashan.sayPoims();
        System.out.println("------------------");

        AbstractPlace place = new PlayPlace("故宫").getPlace();



    }
}
