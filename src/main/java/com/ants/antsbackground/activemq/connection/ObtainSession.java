package com.ants.antsbackground.activemq.connection;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;

/**
 * @Author czd
 * @Date:createed in
 * @Version: V1.0
 */
public class ObtainSession {
    private  Session session;
    private Connection connection;

    public  Session createSession(){
       try {
           //创建连接工厂
           ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("admin","admin","tcp://127.0.0.1:61616");
           //通过工厂创建连接
           connection = connectionFactory.createConnection();
           //启动连接
           connection.start();
           //创建会话
           this.session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        } catch (JMSException e) {
            e.printStackTrace();
        }
       return session;
    }

    public void close(){
        if (connection != null){
            try {
                connection.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
