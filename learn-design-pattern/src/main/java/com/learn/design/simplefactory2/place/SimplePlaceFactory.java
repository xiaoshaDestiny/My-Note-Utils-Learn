package com.learn.design.simplefactory2.place;

import com.learn.design.simplefactory2.pizzacase.GreekPizza;

/**
 * @author xrb
 * @create 2020-03-30 17:38
 */
public class SimplePlaceFactory {

    public static AbstractPlace createPlace(String placeName){
        AbstractPlace abstractPlace = null;

        if(placeName.equals("华山")){
            abstractPlace = new HuaShanPlace();
            abstractPlace.setPlaceName(placeName);
        }
        if(placeName.equals("故宫")){
            abstractPlace = new GuGongPlace();
            abstractPlace.setPlaceName(placeName);
        }

        return abstractPlace;
    }
}
