package com.ants.antsbackground.activemq.ptp;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * @Author czd
 * @Date:createed in
 * @Version: V1.0
 */
public class Consumer {
    public static void main(String[] args) {
        reciveMessage();

    }

    public static void  reciveMessage(){
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
            //创建消息接收者
            MessageConsumer messageConsumer = session.createConsumer(destination);

            messageConsumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    ObjectMessage objectMessage = (ObjectMessage)message;
                    try {
                        Object object = objectMessage.getObject();
                        System.out.println(object);
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });
            try {
                System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
