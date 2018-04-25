package com.gyg.jms.testQueue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * <pre>
 * 功    能: $comment$
 * 涉及版本:
 * 创 建 者: 古粤赣
 * 日    期: 2017年11月14日  10:02:36
 * Q    Q: 1784286916
 * </pre>
 */
public class MsgConsumer {

    private static final String URL = "tcp://127.0.0.1:61616";
    private static final String QUEUE_NAME = "queue-test";

    public static void main(String[] args) throws Exception{
        //1. 创建ConnectionFactory
        ActiveMQConnectionFactory activeMQConnectionFactory
                = new ActiveMQConnectionFactory();
        //2. 创建Connection
        Connection connection = activeMQConnectionFactory.createConnection();
        //3. 启动连接
        connection.start();
        //4. 创建会话 【Session.AUTO_ACKNOWLEDGE：自动应答】
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5. 创建一个目标
        Destination destination = session.createQueue(QUEUE_NAME);
        //6. 创建一个生产者
        MessageConsumer consumer = session.createConsumer(destination);
        //7. 监听消息
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                // 8. 将接收的消息强转
                TextMessage txtMsg = (TextMessage) message;

                try {
                    System.out.println("consumer: " + txtMsg.getText());
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
