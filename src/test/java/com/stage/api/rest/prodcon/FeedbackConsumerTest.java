package com.stage.api.rest.prodcon;

import java.util.Collections;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.TopicPartition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.stage.FeedbackGiven;
import com.stage.api.rest.infrastructure.FeedbackConsumer;
import com.stage.api.rest.properties.ConsumerProperties;
import com.stage.api.rest.properties.KafkaTopicProperties;
import com.stage.api.rest.service.FeedbackService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = FeedbackConsumer.class)
@ActiveProfiles("test")
public class FeedbackConsumerTest {	
	
    @Autowired
    private FeedbackConsumer feedbackConsumer;
    
    @MockBean
    private KafkaTopicProperties kafkaProperties;
    
    @MockBean
    private FeedbackService feedbackService;
    
    @MockBean
    private KafkaTemplate<String, FeedbackGiven> kafkaTemplate;
    
    @MockBean
    private ConsumerProperties properties;
    
    @Test
    public void putFeedbackInDatabase() {
    	FeedbackGiven feedback = new FeedbackGiven();
        feedback.setFeedbackID("JoranNieuwpoort1");
        feedback.setLocation("Nieuwpoort");
        feedback.setUsername("Joran");
        feedback.setComment("The waves are amazing");
        feedback.setSentAt(1);
        
        ConsumerRecord<String, FeedbackGiven> record = new ConsumerRecord<>("test", 1, 0L, feedback.getFeedbackID(), feedback);
        ConsumerRecords<String, FeedbackGiven> records = new ConsumerRecords<>(Collections.singletonMap(new TopicPartition(kafkaProperties.getTopicFeedback(), 1), Collections.singletonList(record)));        
        
		Mockito.doNothing().when(feedbackService).postFeedbackInDatabase(feedback);
		
        Assertions.assertDoesNotThrow(() -> feedbackConsumer.putFeedbackInDatabase(feedback));
		
    }
}
