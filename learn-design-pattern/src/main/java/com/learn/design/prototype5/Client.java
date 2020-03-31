package com.learn.design.prototype5;

/**
 * @author xrb
 * @create 2020-03-31 13:33
 */
public class Client {
    public static void main(String[] args) {
//        System.out.println("=============== 原来的写法 ===============");
//        Sheep sheep = new Sheep("tom", 1, "black");
//        Sheep sheep1 = new Sheep(sheep.getName(), sheep.getAge(), sheep.getColor());
//        Sheep sheep2 = new Sheep(sheep.getName(), sheep.getAge(), sheep.getColor());
//        Sheep sheep3 = new Sheep(sheep.getName(), sheep.getAge(), sheep.getColor());
//        System.out.println(sheep);
//        System.out.println(sheep1);
//        System.out.println(sheep2);
//        System.out.println(sheep3);

//        System.out.println("=============== 原型设计模式写法 实现Cloneable接口 ===============");
//        Sheep duoli = new Sheep("多莉", 1, "白色");
//        Sheep clone = duoli.clone();
//        Sheep clone1 = duoli.clone();
//        Sheep clone2 = duoli.clone();
//        Sheep clone3 = duoli.clone();
//        System.out.println(duoli);
//        System.out.println(clone);
//        System.out.println(clone1);
//        System.out.println(clone2);
//        System.out.println(clone3);

        System.out.println("=============== 原型设计模式写法 实现Cloneable接口 ===============");
        Sheep sheep = new Sheep("小山羊", 1, "black");
        sheep.setFriend(new Sheep("小盘羊", 1, "white"));

        Sheep clone1 = sheep.clone();
        Sheep clone2 = sheep.clone();

        System.out.println(clone1 + " 克隆羊的hashcode:"+clone1.getFriend().hashCode());
        System.out.println(clone2 + " 克隆羊的hashcode:"+clone2.getFriend().hashCode());

        //hashcode是一样的，clone对象属性的时候，是一个指向该对象的引用。

    }
}
