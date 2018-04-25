package com.gyg.jms.testQueue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * <pre>
 * 功    能: $comment$
 * 涉及版本:
 * 创 建 者: 古粤赣
 * 日    期: 2017年11月14日  09:33:19
 * Q    Q: 1784286916
 * </pre>
 */
public class MsgProducer {

    private static final String URL = "tcp://127.0.0.1:61616";
    private static final String QUEUE_NAME = "queue-test";

    public static void main(String[] args) throws Exception{
        //1. 创建ConnectionFactory
        ConnectionFactory connectionFactory
                = new ActiveMQConnectionFactory(URL);
        //2. 创建Connection
        Connection connection = connectionFactory.createConnection();
        //3. 启动连接
        connection.start();
        //4. 创建会话 【Session.AUTO_ACKNOWLEDGE：自动应答】
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5. 创建一个目标
        Destination destination = session.createQueue(QUEUE_NAME);
        //6. 创建一个生产者
        MessageProducer producer = session.createProducer(destination);
        for (int i = 0; i < 100; i++) {
            //7. 创建消息
            TextMessage textMessage = session.createTextMessage("text: " + i);
            //8. 发布消息
            producer.send(textMessage);

            System.out.println("producer: " + textMessage.getText());
        }
        //9. 关闭连接
        connection.close();
    }
}
