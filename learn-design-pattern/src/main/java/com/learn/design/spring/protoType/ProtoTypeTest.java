package com.learn.design.spring.protoType;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author xrb
 * @create 2020-03-31 16:42
 */
public class ProtoTypeTest {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
        Object bean = ctx.getBean("id01");
        System.out.println(bean);

        Object bean1 = ctx.getBean("id01");
        System.out.println(bean1);

        System.out.println(bean == bean1);
        System.out.println(bean.hashCode());
        System.out.println(bean1.hashCode());

    }
}
