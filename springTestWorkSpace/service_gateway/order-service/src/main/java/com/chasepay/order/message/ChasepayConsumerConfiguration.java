package com.chasepay.order.message;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;


@Configuration
@EnableKafka
public class ChasepayConsumerConfiguration {
	
	@Value("${spring.kafka.bootstrap-servers}")
	String servers;
	
	@Value("${spring.kafka.consumer.group-id}")
	String group_id;
	
	@Value("${spring.kafka.consumer.enable-auto-commit}")
	String enable_auto_commit;
	
	@Value("${spring.kafka.consumer.auto-offset-reset}")
	String auto_offset_reset;
	
	@Value("${spring.kafka.consumer.max-poll-records}")
	String max_poll;
	
	@Value("${spring.kafka.consumer.key-deserializer}")
	String key_deserializer;
	
	@Value("${spring.kafka.consumer.value-deserializer}")
	String value_deserializer;
	
	
	//@Value("${spring.kafka.listener.batch-listener}")
	//String batch_listener;
	
	@Value("${spring.kafka.listener.concurrencys}")
	String concurrencys;
	
	@Value("${spring.kafka.listener.topic}")
	String topic;

	
	
	public Map<String,Object> getConsumerProperties()
	{
		Map<String,Object> map = new HashMap<String, Object>();
		//map.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, autoCommitInterval);
		
		map.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
		map.put(ConsumerConfig.GROUP_ID_CONFIG, group_id);
	    map.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enable_auto_commit);
	    map.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, max_poll);
	    map.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, auto_offset_reset);
	    //map.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeout);
	    //map.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, maxPollInterval);
	    //map.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, maxPartitionFetchBytes);
	    map.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
	    map.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

		return map;
	}
	
	//@Bean
	public ConsumerFactory<String, String> consumerFactory()
	{
		Map<String, Object> consumerProperties = getConsumerProperties();
		DefaultKafkaConsumerFactory<String, String> defaultKafkaConsumerFactory = new DefaultKafkaConsumerFactory<>(consumerProperties);
		return defaultKafkaConsumerFactory;
	}
	
	
	//@Bean
	public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory()
	{
		ConcurrentKafkaListenerContainerFactory<String, String> concurrentKafkaListenerContainerFactory = new ConcurrentKafkaListenerContainerFactory<>();
		concurrentKafkaListenerContainerFactory.setConsumerFactory(consumerFactory());
		concurrentKafkaListenerContainerFactory.setConcurrency(3);
		//concurrentKafkaListenerContainerFactory.setBatchListener(true);
		concurrentKafkaListenerContainerFactory.getContainerProperties().setPollTimeout(3000);
		
		return concurrentKafkaListenerContainerFactory;
	}

}
