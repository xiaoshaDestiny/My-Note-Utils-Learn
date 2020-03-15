package com.learn.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * @author xrb
 * @create 2020-03-15 16:20
 */
public class JmsConsumer {
    public static final String ACTIVEMQ_URL = "tcp://127.0.0.1:61616";
    public static final String QUEUE_NAME = "myQueue01";

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
        Queue queue = session.createQueue(QUEUE_NAME);

        //上边是一样的
        /***************************************************************/

        //5、创建消费者
        MessageConsumer messageConsumer = session.createConsumer(queue);

        /**
         * receive()
         * 同步阻塞方式
         * 订阅者或接受者调用MessageConsumer的receive()方法来接收消息，
         * receive方法在能够接收到消息之前（或超时之前）将一直阻塞
         */
       /* 
         while(true){

            //等一段时间，3秒
            //messageConsumer.receive(3000);
            //一直等，死等
            TextMessage textMessage =(TextMessage) messageConsumer.receive();

            if(textMessage != null){
                System.out.println("消费者受到消息为："+textMessage.getText());
            }else {
                break;
            }
        }
        messageConsumer.close();
        session.close();
        connection.close();*/

        /**
         * 异步非阻塞
         * 通过监听器的方式来消费消息，MessageConsumer messageConsumer= session.createConsumer(queue);
         */
       messageConsumer.setMessageListener(new MessageListener(){
           @Override
           public void onMessage(Message message) {
               if(message != null && message instanceof TextMessage){
                   TextMessage textMessage =(TextMessage) message;
                   try {
                       System.out.println("消费者监听到消息："+textMessage.getText());
                   } catch (JMSException e) {
                       e.printStackTrace();
                   }
               }
           }
       });
       System.in.read();
       messageConsumer.close();
       session.close();
       connection.close();
    }
}
