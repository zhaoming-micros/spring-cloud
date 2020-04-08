package com.chasepay.order.message;

import java.util.Optional;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
public class MessageReceiver {
	
	private static Logger logger = LogManager.getLogger(MessageReceiver.class);
	
	
	//高版本的kafka才支持批量消费
	/*@KafkaListener(topics="${spring.kafka.listener.topic}",containerFactory="bathListenerContainerFactory")
    public void listen(List<ConsumerRecord<String, String>> records,Acknowledgment ack)
    {
    	try 
    	{
    		logger.info("records size = "+records.size());
			for (ConsumerRecord<String, String> consumerRecord : records) 
			{
				String message = consumerRecord.value();
				logger.info("message = "+message);
			}
		} 
    	catch (Exception e)
    	{
    		logger.error("error to receive message.");
			logger.error(e.getMessage(),e);
		}
    	finally
    	{
    		ack.acknowledge();
    	}
    }*/
	
	
	
	@KafkaListener(topics="carl_topic1")
    public void listen(ConsumerRecord<String, String> record)
    {
    	try 
    	{
    		logger.info("receive single record.");
    		Optional<String> kafkaMessage = Optional.ofNullable(record.value());
    		if(kafkaMessage.isPresent())
    		{
    			String message = kafkaMessage.get();
    			logger.info("message = "+message);
    		}
		} 
    	catch (Exception e)
    	{
    		logger.error("error to receive message.");
			logger.error(e.getMessage(),e);
		}

    }
}
