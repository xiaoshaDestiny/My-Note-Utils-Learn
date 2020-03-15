package com.learn.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author xrb
 * @create 2020-03-15 16:20
 */
public class JmsConsumer {
    public static final String ACTIVEMQ_URL = "tcp://127.0.0.1:61616";
    public static final String QUEUE_NAME = "myQueue01";

    public static void main(String[] args) throws JMSException {
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
        connection.close();
    }
}
