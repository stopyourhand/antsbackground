package com.ants.antsbackground.activemq.pot;

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

        Session session = null;
        Destination destination =null;
        MessageConsumer messageConsumer = null;
        Message message = null;

        try {
            ObtainSession obtainSession = new ObtainSession();
            //创建会话
            session = obtainSession.createSession();
            //创建会话主题
            destination = session.createQueue("helloWorld");
            //创建消息消费者体
            messageConsumer = session.createConsumer(destination);
            //创建消息体
            message = messageConsumer.receive();

            //消息接受确认
            message.acknowledge();

            System.out.println(((TextMessage)message).getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }finally {
            if (messageConsumer != null) {
                try {
                    messageConsumer.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
            if (session != null){
                try {
                    session.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
