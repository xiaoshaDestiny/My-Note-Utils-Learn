package com.learn.spring;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * @author xrb
 * @create 2020-03-18 20:48
 */
@Service
public class SpringMqProduce {
    @Autowired
    private JmsTemplate jmsTemplate;

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        SpringMqProduce produce = (SpringMqProduce) ctx.getBean("springMqProduce");

        produce.jmsTemplate.send( (Session session) ->{
            TextMessage textMessage = session.createTextMessage("... spring 整合 activemq  33333...");
            return textMessage;
        });
        System.out.println("send over!");
    }

}
