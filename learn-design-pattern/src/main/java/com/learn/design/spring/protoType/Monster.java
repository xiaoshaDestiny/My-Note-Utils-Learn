package com.learn.design.spring.protoType;

import com.learn.design.prototype5.Sheep;

/**
 * @author xrb
 * @create 2020-03-31 16:37
 */
public class Monster {
    private Integer id = 10;
    private String name = "金角大王";
    private String skill = "葫芦";

    public Monster() {
        System.out.println("create bean ... ");
    }

    public Monster(Integer id, String name, String skill) {
        this.id = id;
        this.name = name;
        this.skill = skill;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    @Override
    public String toString() {
        return "Monster{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", skill='" + skill + '\'' +
                '}';
    }
}
