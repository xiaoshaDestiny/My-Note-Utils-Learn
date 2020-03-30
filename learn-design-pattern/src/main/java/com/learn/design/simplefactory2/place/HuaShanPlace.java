package com.learn.design.simplefactory2.place;

/**
 * @author xrb
 * @create 2020-03-30 17:35
 */
public class HuaShanPlace extends AbstractPlace {
    @Override
    public void getTicket() {
        System.out.println("买华山景区的票。。");
    }

    public void sayPoims(){
        System.out.println("华山吟诗。。。");
    }
}
