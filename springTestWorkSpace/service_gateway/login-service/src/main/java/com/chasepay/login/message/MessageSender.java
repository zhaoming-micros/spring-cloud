package com.chasepay.login.message;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;


@Component
public class MessageSender {
	
	private static Logger logger = LogManager.getLogger(MessageSender.class);
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	
	
	public void sendMessage(String topic,String message)
	{
		logger.info("start to send message "+message);
		ListenableFuture<SendResult<String, String>> result = kafkaTemplate.send(topic, message);
		result.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

			@Override
			public void onSuccess(SendResult<String, String> arg0) {
				logger.info("succeed to send message. message = "+message);
			}

			@Override
			public void onFailure(Throwable arg0) {
				logger.error("error to send message.");
				logger.error(arg0.getMessage(),arg0);
			}
		});
	}

}
