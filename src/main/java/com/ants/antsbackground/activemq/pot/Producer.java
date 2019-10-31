package com.ants.antsbackground.activemq.pot;

import com.ants.antsbackground.activemq.connection.ObtainSession;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * ActiveMQ主动消费
 *
 * @Author czd
 * @Date:createed in 2019/10/31
 * @Version: V1.0
 */
public class Producer {
    public static void main(String[] args) {
        sendMessage("Hello ActiveMQ!");
    }
    public static void sendMessage(String data) {
        Session session = null;
        Destination destination = null;
        MessageProducer messageProducer = null;
        Message message = null;
        try {
            ObtainSession obtainSession = new ObtainSession();
            //创建会话
            session = obtainSession.createSession();
            //创建目的地,也就是主题
            destination = session.createQueue("helloWorld");
            //创建消息发送者,通过会话创建消息发送者,把目的地放进消息发送者那里
            messageProducer = session.createProducer(destination);
            //创建保存消息的主体
            message = session.createTextMessage(data);

            messageProducer.send(message);
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            if (messageProducer != null) {
                try {
                    messageProducer.close();
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
