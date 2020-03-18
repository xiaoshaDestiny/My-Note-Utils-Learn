package com.learn.activemq.broker;

import org.apache.activemq.broker.BrokerService;

/**
 * @author xrb
 * @create 2020-03-18 20:34
 */
public class EmbedBroker {
    public static void main(String[] args) throws Exception {
        BrokerService brokerService = new BrokerService();
        brokerService.setUseJmx(true);
        brokerService.addConnector("tcp://localhost:61616");
        brokerService.start();

    }
}
