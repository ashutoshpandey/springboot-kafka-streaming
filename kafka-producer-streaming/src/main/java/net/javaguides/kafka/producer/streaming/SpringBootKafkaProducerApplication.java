package net.javaguides.kafka.producer.streaming;

import net.javaguides.kafka.producer.streaming.service.StreamingProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootKafkaProducerApplication implements CommandLineRunner {
    @Autowired
    private StreamingProducer producer;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootKafkaProducerApplication.class);
    }

    @Override
    public void run(String... args) throws Exception {
        producer.sendMessage();
    }
}
