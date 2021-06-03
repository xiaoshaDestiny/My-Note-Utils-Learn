package com.learn.boot.dto;

import lombok.ToString;


@ToString
public class Tourist {

    private String name;

    private Integer age;

    private Box box;

    private Baggage pack;

    public Tourist(String name, Integer age, Box box, Baggage pack) {
        this.name = name;
        this.age = age;
        this.box = box;
        this.pack = pack;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Box getBox() {
        return box;
    }

    public void setBox(Box box) {
        this.box = box;
    }

    public Baggage getPack() {
        return pack;
    }

    public void setPack(Baggage pack) {
        this.pack = pack;
    }
}
