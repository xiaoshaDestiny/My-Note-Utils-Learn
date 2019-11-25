package com.learn.jvm;

import jdk.nashorn.internal.objects.annotations.Getter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author xrb
 * @create 2019-11-15 11:26
 *
 * 原子引用，如果想对某个类进行原子包装，可以使用原子引用类 AtomicReference
 *
 *
 * ABA问题和原子引用
 *
 *
 *
 *
 */

class User{
    private String username;
    private int age;

    public User(String username, int age) {
        this.username = username;
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" + "username='" + username + '\'' + ", age=" + age + '}';
    }
}

public class AtomicReferenceDemo {
    public static void main(String[] args) {
        User z3 = new User("z3",22);
        User li4 = new User("li4",24);

        AtomicReference<User> atomicReference = new AtomicReference<>();

        //设置z3对象的原子引用 放在主物理内存中
        atomicReference.set(z3);

        //比较当前值z3和内存中的值是否一致，一致则将其改变为li4对象   比较并且交换
        //atomicReference.compareAndSet(z3,l4);
        System.out.println(atomicReference.compareAndSet(z3,li4)+"\t"+atomicReference.get().toString());
        System.out.println(atomicReference.compareAndSet(z3,li4)+"\t"+atomicReference.get().toString());
    }
}
