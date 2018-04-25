package com.gyg.jms.testTopic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * <pre>
 * 功    能: $comment$
 * 涉及版本:
 * 创 建 者: 古粤赣
 * 日    期: 2017年11月14日  13:36:35
 * Q    Q: 1784286916
 * </pre>
 */
public class MsgProducer {

    private static final String URL = "tcp://127.0.0.1:61616";
    private static final String TOPIC_NAME = "topic-test";

    public static void main(String[] args) throws Exception{
        //1. 创建ActiveMQ的连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(URL);
        //2. 创建连接
        Connection connection = connectionFactory.createConnection();
        //3. 开启连接
        connection.start();
        //4. 创建会话
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5. 创建一个目标，告诉生产者消息发送到哪里
         Destination destination = session.createTopic(TOPIC_NAME);
        //6. 创建生产者
        MessageProducer producer = session.createProducer(destination);
        for (int i = 0; i < 100; i++) {
            //7. 创建消息
            TextMessage textMessage = null;
            try {
                textMessage = session.createTextMessage("producer: " + i);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //8. 发布消息
            producer.send(textMessage);

            System.out.println(textMessage.getText());
        }
        //-2. 关闭连接
        connection.close();
    }
}
