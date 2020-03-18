package com.learn.spring;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

/**
 * @author xrb
 * @create 2020-03-18 20:48
 */
@Service
public class SpringMqConsumer {
    @Autowired
    private JmsTemplate jmsTemplate;

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        SpringMqConsumer springMqConsumer = (SpringMqConsumer) ctx.getBean("springMqConsumer");
        String str  = (String) springMqConsumer.jmsTemplate.receiveAndConvert();
        System.out.println("消费者受到消息"+str);
    }
}
