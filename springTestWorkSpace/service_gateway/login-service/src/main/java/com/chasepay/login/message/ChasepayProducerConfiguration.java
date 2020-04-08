package com.chasepay.login.message;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
@EnableKafka
public class ChasepayProducerConfiguration {
	
	@Value("${spring.kafka.bootstrap-servers}")
	String servers;
	
	@Value("${spring.kafka.producer.acks}")
	String acks;
	
	@Value("${spring.kafka.producer.retries}")
	String retries;
	
	@Value("${spring.kafka.producer.batch-size}")
	String batch_size;
	
	@Value("${spring.kafka.producer.properties.linger.ms}")
	String linger;
	
	@Value("${spring.kafka.producer.buffer-memory}")
	String buffer_memory;
	

	
	
	public Map<String,Object> getProducerProperties()
	{
		Map<String,Object> map = new HashMap<String, Object>();
		map.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
		map.put(ProducerConfig.ACKS_CONFIG, acks);
		map.put(ProducerConfig.RETRIES_CONFIG, retries);
		map.put(ProducerConfig.BATCH_SIZE_CONFIG, batch_size);
		map.put(ProducerConfig.LINGER_MS_CONFIG, linger);
		map.put(ProducerConfig.BUFFER_MEMORY_CONFIG, buffer_memory);
		map.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		map.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

		return map;
	}
	
	
	public ProducerFactory<String, String> getProducerFactory()
	{
		Map<String, Object> producerProperties = getProducerProperties();
		DefaultKafkaProducerFactory<String, String> defaultKafkaProducerFactory = new DefaultKafkaProducerFactory<>(producerProperties);
		return defaultKafkaProducerFactory;
	}
	
	
	@Bean
	public KafkaTemplate<String, String> kafkaTemplate()
	{
		return new KafkaTemplate<>(getProducerFactory());
	}

}
