package com.learn.boot.dto;

import lombok.ToString;

/**
 * @author xu.rb
 * @description: TODO
 * @since 2021-06-03 22:46
 */
@ToString
public class Baggage {
    private String color;
    private Box box;
    private String no;

    public Baggage(String color, Box box, String no) {
        this.color = color;
        this.box = box;
        this.no = no;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Box getBox() {
        return box;
    }

    public void setBox(Box box) {
        this.box = box;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }
}
