package com.learn;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author xu.rb
 * @since 2020-05-12 17:01
 */
public class MainTest {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        A a = (A)ctx.getBean("a");
        System.out.println("result a : "+a);
        System.out.println("result a->b : "+a.getB());

        B b = (B)ctx.getBean("b");
        System.out.println("result b : "+b);
        System.out.println("result b->a : "+b.getA());
    }
}
