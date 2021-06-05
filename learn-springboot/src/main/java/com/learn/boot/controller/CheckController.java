package com.learn.boot.controller;


import com.google.common.collect.Lists;
import com.learn.boot.dto.Baggage;
import com.learn.boot.dto.Box;
import com.learn.boot.dto.Tourist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author xu.rb
 * @description: TODO
 * @since 2021-05-15 01:24
 */
@RestController
public class CheckController {

    @Autowired
    private CheckService checkService;

    private static List<Tourist> touristLists;

    private static List<Baggage> baggageList;

    static {
        Box blackBox = new Box("black", 20);
        Box redBox = new Box("red", 10);

        Baggage blackBaggage = new Baggage("black", redBox, "001");
        Baggage redBaggage = new Baggage("red", blackBox, "002");
        Baggage blueBaggage = new Baggage("black", blackBox, "003");

        Tourist tom = new Tourist("tom", 1, redBox, redBaggage);
        Tourist jerry = new Tourist("jerry", 2, blackBox, blueBaggage);
        Tourist donald = new Tourist("donald", 3, redBox, blackBaggage);

        touristLists = Lists.newArrayList(tom, jerry, donald);
        baggageList = Lists.newArrayList(blackBaggage, redBaggage, blueBaggage);
    }

    @RequestMapping(value = "/tour/check")
    public String checkTour(@RequestParam("name") String name){
        Optional<Tourist> first = touristLists.stream()
                .filter(tour -> tour.getName().equalsIgnoreCase(name))
                .findFirst();
        return first.isPresent() ? checkService.tourService(first.get()) : "empty";
    }


    @RequestMapping(value = "/pak/check")
    public String pakTour(@RequestParam("no") String no){
        Map<String, Tourist> packToTour = touristLists.stream()
                .collect(Collectors.toMap(tour -> tour.getPack().getNo(), Function.identity()));
        Optional<Baggage> first = baggageList.stream().filter(bag -> bag.getNo().equalsIgnoreCase(no))
                .findFirst();
        return first.isPresent() ? checkService.baggageService(first.get(), packToTour.get(no)) : "empty";
    }
}
