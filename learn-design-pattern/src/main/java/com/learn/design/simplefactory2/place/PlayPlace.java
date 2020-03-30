package com.learn.design.simplefactory2.place;

/**
 * @author xrb
 * @create 2020-03-30 17:43
 */
public class PlayPlace {

    AbstractPlace abstractPlace;

    public PlayPlace(String placeName) {
        abstractPlace = SimplePlaceFactory.createPlace(placeName);
        if(abstractPlace != null){
            abstractPlace.getTicket();
            abstractPlace.photo();
        }else {
            System.out.println("游玩失败");
        }
    }

    public AbstractPlace getPlace(){
        return abstractPlace;
    }

}
