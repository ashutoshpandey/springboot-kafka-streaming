package net.javaguides.kafka.producer.streaming.service;

import com.launchdarkly.eventsource.EventSource;
import com.launchdarkly.eventsource.StreamException;
import com.launchdarkly.eventsource.background.BackgroundEventHandler;
import com.launchdarkly.eventsource.background.BackgroundEventSource;
import net.javaguides.kafka.producer.streaming.handler.StreamingEventHandler;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;

import java.net.URI;
import java.util.concurrent.TimeUnit;

@Service
public class StreamingProducer {
    @Value("${kafka.producer.topic}")
    private String topicName;

    private static final Logger LOGGER = LoggerFactory.getLogger(StreamingProducer.class);

    private KafkaTemplate<String, String> kafkaTemplate;

    public StreamingProducer(KafkaTemplate kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage() throws InterruptedException {
        // To read real time stream data from wikimedia, we use event source

        BackgroundEventHandler eventHandler = new StreamingEventHandler(kafkaTemplate);
        String url = "https://stream.wikimedia.org/v2/stream/recentchange";

        EventSource.Builder eventSourceBuilder = new EventSource.Builder(URI.create(url));
        BackgroundEventSource.Builder builder = new BackgroundEventSource.Builder(eventHandler, eventSourceBuilder);
        BackgroundEventSource eventSource = builder.build();
        eventSource.start();

        TimeUnit.MINUTES.sleep(1);
    }
}
