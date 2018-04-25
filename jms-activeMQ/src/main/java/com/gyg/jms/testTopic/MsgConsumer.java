package com.gyg.jms.testTopic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * <pre>
 * 功    能: $comment$
 * 涉及版本:
 * 创 建 者: 古粤赣
 * 日    期: 2017年11月14日  13:36:49
 * Q    Q: 1784286916
 * </pre>
 */
public class MsgConsumer {

    private static final String URL = "tcp://127.0.0.1:61616";
    private static final String TOPIC_NAME = "topic-test";

    public static void main(String[] args) throws Exception{
        //1. 创建ActiveMQ连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(URL);
        //2. 创建连接
        Connection connection = connectionFactory.createConnection();
        //3. 开启连接
        connection.start();
        //4. 创建会话
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5. 创建一个目标，告诉消费者去哪里消费
        Destination destination = session.createTopic(TOPIC_NAME);
        //6. 创建消费者
        MessageConsumer consumer = session.createConsumer(destination);
        //7. 订阅消息
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                //8. 将获取的消息强转
                TextMessage txtMsg = (TextMessage) message;

                try {
                    System.out.println(txtMsg.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

        /**不能在这里关闭连接，消息监听是异步过程，
         * 这里关闭连接，消费者不能再监听到消息，
         * 应该在程序退出时关闭连接
         */
        //connection.close();
    }
}
