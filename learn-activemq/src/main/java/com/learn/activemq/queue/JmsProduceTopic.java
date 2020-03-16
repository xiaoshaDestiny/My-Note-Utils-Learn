package com.learn.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author xrb
 * @create 2020-03-15 20:27
 * 主题生产者
 */
public class JmsProduceTopic {
    public static final String ACTIVEMQ_URL = "tcp://127.0.0.1:61616";
    public static final String TOPIC_NAME = "topic01";


    public static void main(String[] args) throws JMSException {

        //1、创建链接工厂，按照给定的URL地址，采用默认的用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory =
                new ActiveMQConnectionFactory(ACTIVEMQ_URL);

        //2、通过链接工厂，获得链接connection并启动访问
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        //3、创建回话session
        //第一个参数叫事务  第二个参数叫签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //4、创建目的地（具体是主题还是队列） Destination是接口  Queue和Topic是实现
        Topic topic = session.createTopic(TOPIC_NAME);

        //5、创建消息的生产者
        MessageProducer messageProducer = session.createProducer(topic);

        //6、 发送消息  消息生产者生产三条消息，发送到mq的队列里面
        for (int i = 1;i <=6 ; i++) {

            //7、创建消息
            //字符串
            TextMessage textMessage = session.createTextMessage("TOPIC_NAME---" + i);

            //8、通过消息生产者发布   通过messageProducer 发送给MQ
            messageProducer.send(textMessage);
        }

        //9、关闭资源
        messageProducer.close();
        session.close();
        connection.close();

        System.out.println("************消息生产并且成功发送到MQ************");
    }

}
