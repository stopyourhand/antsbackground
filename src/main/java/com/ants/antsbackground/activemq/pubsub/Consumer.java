package com.ants.antsbackground.activemq.pubsub;

import com.ants.antsbackground.activemq.connection.ObtainSession;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @Author czd
 * @Date:createed in
 * @Version: V1.0
 */
public class Consumer {
    public static void main(String[] args) {
        reciveMessage();
    }

    public static void reciveMessage(){
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("admin","admin","tcp://127.0.0.1:61616");

        //创建消息消费者
        MessageConsumer messageConsumer = null;
        try {
            Connection connection = connectionFactory.createConnection();
            //启动连接
            connection.start();
            //创建会话
            Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
            //创建目的地，主题
            Destination destination = session.createTopic("topicTest");
            messageConsumer = session.createConsumer(destination);

            //通过消费者接受到的消息传入一个消息体中
            Message message = messageConsumer.receive();

//            message.acknowledge();
            System.out.println(((TextMessage)message).getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
