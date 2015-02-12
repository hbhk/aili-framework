package org.hbhk.aili.jms.server;

import java.util.concurrent.atomic.AtomicInteger;

import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;

public class TestVirtualTopic {

	public static void main(String[] args) {
		try {

			ActiveMQConnectionFactory factoryA = new ActiveMQConnectionFactory(
					"tcp://127.0.0.1:61616");

			Queue queue = new ActiveMQQueue(getVirtualTopicConsumerNameA());
			ActiveMQConnection conn = (ActiveMQConnection) factoryA
					.createConnection();
			conn.start();
			Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);

			MessageConsumer consumer1 = session.createConsumer( queue );
			MessageConsumer consumer2 = session.createConsumer( queue );
			MessageConsumer consumer3 = session.createConsumer( new ActiveMQQueue(getVirtualTopicConsumerNameB()) );
			final AtomicInteger aint1 = new AtomicInteger(0);
			MessageListener listenerA = new MessageListener() {
				public void onMessage(Message message) {
					try {
						System.out.println(aint1.incrementAndGet()
								+ " => receive from "+ getVirtualTopicConsumerNameA() +": " + message);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			consumer1.setMessageListener(listenerA);
			consumer2.setMessageListener(listenerA);
			final AtomicInteger aint2 = new AtomicInteger(0);
			MessageListener listenerB = new MessageListener() {
				public void onMessage(Message message) {
					try {
						System.out.println(aint2.incrementAndGet()
								+ " => receive from "+ getVirtualTopicConsumerNameB() +": " + message);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			consumer3.setMessageListener(listenerB);
			
			MessageProducer producer = session.createProducer(new ActiveMQTopic(getVirtualTopicName()));
			int index = 0;
			while (index++ < 100) {
				TextMessage message = session.createTextMessage(index
						+ " message.");
				producer.send(message);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	  protected static String getVirtualTopicName() {
	        return "VirtualTopic.TEST";
	    }

	    protected static String getVirtualTopicConsumerNameA() {
	        return "Consumer.A.VirtualTopic.TEST";
	    }
	    
	    protected static String getVirtualTopicConsumerNameB() {
	        return "Consumer.B.VirtualTopic.TEST";
	    }

}

