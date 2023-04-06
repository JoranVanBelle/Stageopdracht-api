package com.stage.api.rest.prodcon;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.Duration;
import java.util.Collections;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.stage.FeedbackGiven;
import com.stage.api.rest.service.FeedbackService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = FeedbackConsumer.class)
@ActiveProfiles("test")
public class FeedbackConsumerTest {

	@Value("api.feedback.topic")
	private String topic;
	
    @Autowired
    private FeedbackConsumer feedbackConsumer;
    
    @MockBean
    private FeedbackService feedbackService;
    
    @MockBean
    private KafkaConsumer<String, FeedbackGiven> consumer;
    
    @Test
    public void putFeedbackInDatabase() {
    	FeedbackGiven feedback = new FeedbackGiven();
        feedback.setFeedbackID("JoranNieuwpoort1");
        feedback.setLocation("Nieuwpoort");
        feedback.setUsername("Joran");
        feedback.setComment("The waves are amazing");
        feedback.setSentAt(1);
        
        ConsumerRecord<String, FeedbackGiven> record = new ConsumerRecord<>(topic, 1, 0L, feedback.getFeedbackID(), feedback);
        ConsumerRecords<String, FeedbackGiven> records = new ConsumerRecords<>(Collections.singletonMap(new TopicPartition(System.getenv("TOPIC"), 1), Collections.singletonList(record)));        
        
		Mockito.when(consumer.poll(Mockito.any(Duration.class))).thenReturn(records);
		Mockito.doNothing().when(feedbackService).postFeedbackInDatabase(record.value());
		
		feedbackConsumer.putFeedbackInDatabase();

		verify(feedbackService, times(1)).postFeedbackInDatabase(feedback);
    }
}
