package net.javaguides.kafka.producer.streaming.handler;

import com.launchdarkly.eventsource.MessageEvent;
import com.launchdarkly.eventsource.background.BackgroundEventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

public class StreamingEventHandler implements BackgroundEventHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(StreamingEventHandler.class);

    @Value("${kafka.producer.topic}")
    private String topicName;

    private KafkaTemplate<String, String> kafkaTemplate;

    public StreamingEventHandler(KafkaTemplate kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void onOpen() throws Exception {}

    @Override
    public void onClosed() throws Exception {}

    @Override
    public void onMessage(String s, MessageEvent messageEvent) throws Exception {
        LOGGER.info(String.format("Event data -> %s", messageEvent.getData()));

        kafkaTemplate.send(messageEvent.getData(), topicName);
    }

    @Override
    public void onComment(String s) throws Exception {}

    @Override
    public void onError(Throwable throwable) {}
}
