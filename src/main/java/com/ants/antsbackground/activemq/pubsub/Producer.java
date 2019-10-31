package com.ants.antsbackground.activemq.pubsub;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @Author czd
 * @Date:createed in
 * @Version: V1.0
 */
public class Producer {
    public static void main(String[] args) {
        sendMessage("HelloTopic!!!!");
    }
    public static void sendMessage(String data) {
        //连接工厂
        ConnectionFactory connectionFactory = null;
        //连接
        Connection connection = null;
        //目的地
        Destination destination = null;
        //会话
        Session session = null;
        //消息发送者
        MessageProducer messageProducer = null;
        //消息对象
        Message message = null;

        try {
            //创建连接工厂，连接ActiveMQ服务的工厂
            connectionFactory = new ActiveMQConnectionFactory("admin", "admin", "tcp://127.0.0.1:61616");
            //通过工厂创建连接对象
            connection = connectionFactory.createConnection();
            //启动连接
            connection.start();
            /**
             * 通过连接对象，创建会话对象，绑定目的地
             * 有两个参数，第一个是是否支持事务，一般是false,第二个是如何确认消息的处理
             *	session = connection.createSession 或者 Session.AUTO_ACKNOWLEDGE
             */
            session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
            //创建目的地
            destination = session.createTopic("topicTest");

            //确定消息的发送者
            messageProducer = session.createProducer(destination);

            //创建文本消息对象，作为具体数据的载体
            message = session.createTextMessage(data);

            //发送消息到目的地
            messageProducer.send(message);
//			System.out.println("消息发送成功");

        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            try {
                if(messageProducer != null) {
                    messageProducer.close();
                }
                if(session != null) {
                    session.close();
                }
                if(connection != null) {
                    connection.close();
                }

            }catch(Exception e) {
                e.printStackTrace();
            }

        }

    }
}
