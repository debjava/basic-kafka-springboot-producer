package com.ddlab.rnd.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.ddlab.rnd.entity.Employee;

@Configuration
public class KafkaProducerConfig {
	
	@Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;
	
	//1. Send string to Kafka
	
	@Bean
	public ProducerFactory<String, String> producerFactory() {
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
	    return new DefaultKafkaProducerFactory<>(props);
	}
	 
	@Bean
	public KafkaTemplate<String, String> kafkaTemplate() {
	    return new KafkaTemplate<>(producerFactory());
	}
	
	//2. Send Employee objects to Kafka
	
	@Bean
    public ProducerFactory<String, Employee> empProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());
        return new DefaultKafkaProducerFactory<>(configProps);
    }

	@Bean
	public KafkaTemplate<String, Employee> empKafkaTemplate() {
		return new KafkaTemplate<>(empProducerFactory());
	}

}
