package org.hbhk.aili.jms.server;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

public class ReceiverListener {

	public static void main(String[] args) throws InterruptedException {
		run();
	}

	public static void run() {
		// ConnectionFactory ：连接工厂，JMS 用它创建连接
		ConnectionFactory connectionFactory;
		// Connection ：JMS 客户端到JMS Provider 的连接
		Connection connection = null;
		// Session： 一个发送或接收消息的线程
		Session session;
		// Destination ：消息的目的地;消息发送给谁.
		// 消费者，消息接收者
		MessageConsumer consumer;
		connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		try {
			// 构造从工厂得到连接对象
			connection = connectionFactory.createConnection();
			// 启动
			connection.start();
			// 获取操作连接
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue queue = new ActiveMQQueue("hbhk-req");
			consumer = session.createConsumer(queue);
			MessageListener listenerA = new MessageListener() {
				public void onMessage(Message message) {
					try {
						TextMessage textMessage = (TextMessage) message;
						System.out.println(//aint1.incrementAndGet()
								""+ " => receive from " +": " + textMessage.getText()
								);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			consumer.setMessageListener(listenerA);
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}
}
