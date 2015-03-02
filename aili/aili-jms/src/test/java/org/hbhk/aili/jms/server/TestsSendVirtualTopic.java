package org.hbhk.aili.jms.server;

import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;

public class TestsSendVirtualTopic {

	public static void main(String[] args) {
		try {

			ActiveMQConnectionFactory factoryA = new ActiveMQConnectionFactory(
					"tcp://127.0.0.1:61616");

			ActiveMQConnection conn = (ActiveMQConnection) factoryA
					.createConnection();
			conn.start();
			Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);

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

