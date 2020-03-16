package com.learn.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * @author xrb
 * @create 2020-03-15 20:30
 */
public class JmsConsumerTopic {
    public static final String ACTIVEMQ_URL = "tcp://127.0.0.1:61616";
    public static final String TOPIC_NAME = "topic01";

    public static void main(String[] args) throws JMSException, IOException {
        //1、创建链接工厂，按照给定的URL地址，采用默认的用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2、通过链接工厂，获得链接connection并启动访问
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        //3、创建回话session
        //第一个参数叫事务  第二个参数叫签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //4、创建目的地（具体是主题还是队列） Destination是接口  Queue和Topic是实现
        Topic topic = session.createTopic(TOPIC_NAME);


        //5、创建消费者
        MessageConsumer messageConsumer = session.createConsumer(topic);

        messageConsumer.setMessageListener((message) ->{
            if(message != null && message instanceof TextMessage){
                TextMessage textMessage =(TextMessage) message;
                try {
                    System.out.println("消费者监听到消息："+textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        System.in.read();
        messageConsumer.close();
        session.close();
        connection.close();
    }

}
