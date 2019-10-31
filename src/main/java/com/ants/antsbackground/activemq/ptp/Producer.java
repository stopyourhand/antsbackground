package com.ants.antsbackground.activemq.ptp;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @Author czd
 * @Date:createed in
 * @Version: V1.0
 */
public class Producer {
    public static void main(String[] args) {
        sendMessage("hello ptp");
    }
    public static void sendMessage(String data){
        //创建连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("admin","admin","tcp://127.0.0.1:61616");

        try {
            //利用工厂创建连接
            Connection connection = connectionFactory.createConnection();
            //启动连接
            connection.start();
            //创建会话
            Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
            //创建目的地
            Destination destination = session.createQueue("ptp");
            //创建消息发送体
            MessageProducer messageProducer = session.createProducer(destination);
            //创建消息体
            Message message = session.createObjectMessage(data);

            //发送消息体
            messageProducer.send(message);

        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
