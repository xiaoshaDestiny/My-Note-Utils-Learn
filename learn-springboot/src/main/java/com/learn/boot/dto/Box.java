package com.learn.boot.dto;

import lombok.ToString;

/**
 * @author xu.rb
 * @description: TODO
 * @since 2021-06-03 22:46
 */
@ToString
public class Box {
    private String color;
    private Integer size;

    public Box(String color, Integer size) {
        this.color = color;
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
