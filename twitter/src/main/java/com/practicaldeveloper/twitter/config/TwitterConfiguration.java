package com.practicaldeveloper.twitter.config;

import java.net.http.HttpClient;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.impl.client.HttpClients;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.practicaldeveloper.twitter.model.Tweet;

import org.apache.kafka.common.serialization.StringSerializer;




@Configuration
public class TwitterConfiguration {
	
	@Bean
	public NewTopic topic(){
		return TopicBuilder.name("TWEET_TOPIC")
				
				.build();
		
	}
	
	@Bean
    public ProducerFactory<String, Tweet> producerFactory() {
        Map<String, Object> config = new HashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, Tweet> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
    
    @Configuration
    public class JacksonConfiguration { 
        @Bean
        public ObjectMapper objectMapper() {
        	ObjectMapper mapper = new ObjectMapper();
        	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            return JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
        }
    }
	
//	 @Bean
//	 public HttpClient httpClient() {
//	        return HttpClients.custom().build();
//	    }
//
//	  @Bean
//	  public ApacheHttpClient feignClient(HttpClient httpClient) {
//	        return new ApacheHttpClient(httpClient);
//	    }

}
