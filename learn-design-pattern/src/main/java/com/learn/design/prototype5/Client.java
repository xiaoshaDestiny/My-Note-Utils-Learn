package com.learn.design.prototype5;

/**
 * @author xrb
 * @create 2020-03-31 13:33
 */
public class Client {
    public static void main(String[] args) throws CloneNotSupportedException {
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



//        System.out.println("=============== 原型设计模式写法 实现Cloneable接口 ===============");
//        Sheep sheep = new Sheep("小山羊", 1, "black");
//        sheep.setFriend(new Sheep("小盘羊", 1, "white"));
//
//        Sheep clone1 = sheep.clone();
//        Sheep clone2 = sheep.clone();
//
//        System.out.println(clone1 + " 克隆羊的hashcode:"+clone1.getFriend().hashCode());
//        System.out.println(clone2 + " 克隆羊的hashcode:"+clone2.getFriend().hashCode());
        //hashcode是一样的，clone对象属性的时候，是一个指向该对象的引用。



        //clone方式的深拷贝
        DeepProtoType deepProtoType = new DeepProtoType("原型1",new DeepCloneTarget("targer1","target1class"));
        Object clone =(DeepProtoType) deepProtoType.clone();
        System.out.println("对象的toString() :" + deepProtoType + clone);
        System.out.println("对象的hashcode: "+ deepProtoType.hashCode() +"  " + clone.hashCode());

        System.out.println("属性对象的toString() :" + deepProtoType.getDeepCloneTarget() + ((DeepProtoType) clone).getDeepCloneTarget());
        System.out.println("属性对象的hashcode: "+ deepProtoType.getDeepCloneTarget().hashCode() +"  " + ((DeepProtoType) clone).getDeepCloneTarget().hashCode());
        //hashcode不一样 深拷贝

        System.out.println("**********************************************************");

        //序列化的深拷贝
        DeepProtoType d = new DeepProtoType("老牛",new DeepCloneTarget("name老牛","class老牛"));
        DeepProtoType dClone = (DeepProtoType) d.deepClone();
        System.out.println("对象的toString() :" + d + dClone);
        System.out.println("对象的hashcode: "+ d.hashCode() +"  " + dClone.hashCode());

        System.out.println("属性对象的toString() :" + d.getDeepCloneTarget() + ((DeepProtoType) dClone).getDeepCloneTarget());
        System.out.println("属性对象的hashcode: "+ d.getDeepCloneTarget().hashCode() +"  " + ((DeepProtoType) dClone).getDeepCloneTarget().hashCode());




    }
}
