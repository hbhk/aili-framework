package org.hbhk.aili.jms.server.process;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hbhk.aili.jms.server.definition.Configuration;
import org.hbhk.aili.jms.server.transfer.DefaultMessageTransform;
import org.hbhk.aili.jms.server.transfer.IMessageTransform;
import org.hbhk.aili.jms.share.ex.ConvertException;
import org.hbhk.aili.jms.share.ex.JmsBusinessException;
import org.hbhk.aili.jms.share.pojo.ESBHeader;
import org.hbhk.aili.jms.share.pojo.ServiceMessage;
import org.hbhk.aili.jms.share.util.Constant;
import org.hbhk.aili.jms.share.util.GenericsUtils;
import org.hbhk.aili.jms.share.util.HeaderUtils;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class ServerThreadPool {

	/** The Constant CORE_POOL_SIZE. */
	private static final int CORE_POOL_SIZE = 5;
	
	/** The Constant MAX_POOL_SIZE. */
	private static final int MAX_POOL_SIZE = 20;
	
	/** The Constant KEEP_ALIVE_TIME. */
	private static final long KEEP_ALIVE_TIME = 60 * 1000;// 一分钟空闲就回收
	
	/** The Constant UNIT. */
	private static final TimeUnit UNIT = TimeUnit.MILLISECONDS;
	
	/** The Constant QUEUE_SIZE. */
	private static final int QUEUE_SIZE = 1000;

	/** The executor. */
	private ExecutorService executor;
	
	private JmsTemplate jmsTemplate;
	private static final Log logger = LogFactory.getLog(ServerThreadPool.class);
	/**
	 * Instantiates a new server process thread pool.
	 */
	public ServerThreadPool() {
		executor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, UNIT,
				new ArrayBlockingQueue<Runnable>(QUEUE_SIZE));
	}
	public ServerThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit) {
		executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit,
				new ArrayBlockingQueue<Runnable>(QUEUE_SIZE));
	}
	
	public void process(final ServiceMessage message){
		executor.submit(new Runnable() {
			@Override
			public void run() {
				flowProcess(message);
			}
		});
	}
	
	private void flowProcess(ServiceMessage message){
		ESBHeader header = message.getHeader();
		String responseQueue = Configuration.getServiceConfigMap().get(header.getServiceCode()).getResponseQueue();
		String statusQueue = Configuration.getServiceConfigMap().get(header.getServiceCode()).getEsbStatusQueue();
		// 已接收
		if(StringUtils.isNotEmpty(statusQueue)){
			sendStatus(statusQueue,Constant.STATUS_302);
		}
		try {
			// 调用业务处理
			logger.info("调用业务处理开始");
			message = businessProcess(message);
			logger.info("调用业务处理结束");
		} catch (Exception e) {
			logger.error("error", e);
			throw new RuntimeException(e);
		}
		// 业务逻辑处理完成
		if(StringUtils.isNotEmpty(statusQueue)){
			sendStatus(statusQueue,Constant.STATUS_305);
		}
		// 发送响应
		logger.info("发送响应开始");
		sendResponse(responseQueue,message);
		logger.info("发送响应结束");
		// 发送完响应
		if(StringUtils.isNotEmpty(statusQueue)){
			sendStatus(statusQueue,Constant.STATUS_308);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private ServiceMessage businessProcess(ServiceMessage message) throws Exception{
		ESBHeader header = message.getHeader();
		String serviceCode = header.getServiceCode();
		header.setResponseId(UUID.randomUUID().toString());
		IMessageTransform transformer = Configuration.getServiceConfigMap().get(serviceCode).getReqConvertor();
		if(transformer == null){
			transformer = new DefaultMessageTransform();
		}
		Object requestObj = null;
		IProcess process = Configuration.getServiceConfigMap().get(serviceCode).getProcessor();
		// 调用服务端自己编写的处理类来处理请求
		if (process == null) {
			// 空处理
			throw new ConvertException("serviceCode:"+serviceCode+"未配置对应的实例");
		}
		try {
			Class<?> type =GenericsUtils.getSuperInterfaceGenricType(process.getClass());
			requestObj = transformer.toMessage(message.getBody(),type);
		} catch (ConvertException e1) {
			logger.error("error", e1);
			throw e1;
		} catch (UnsupportedEncodingException e) {
			logger.error("error", e);
			throw e;
		}
		
		Object response = null;
		try {
			response = process.process(requestObj);
		} catch (JmsBusinessException e) {
			throw e;
		}
		if(response == null) {
			return new ServiceMessage(header, null);
		}
		String responseStr = null;
		try {
			responseStr = transformer.fromMessage(response);
		} catch (ConvertException e) {
			logger.error("error", e);
			throw e;
		}
		header.setResultCode(1);//成功
		return new ServiceMessage(header, responseStr);
	};
	
	public void sendResponse(String responseQueueName,final ServiceMessage response){
		jmsTemplate.send(responseQueueName, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage message = session.createTextMessage();
				message.setText(response.getBody());
				HeaderUtils.header2JMSProperties(response.getHeader(),
						message);
				return message;
			}
		});
	};


	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
	
	private void sendStatus(String statusQueuName, final String statusId){
		jmsTemplate.send(statusQueuName,new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage = session.createTextMessage();
				textMessage.setText(statusId+"@"+(new Date()).getTime());
				return textMessage;
			}
		});
	}
}
