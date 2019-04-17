package com.hassapp.api.kafka.producer;

import com.hassapp.api.kafka.dto.KafkaModelDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class Sender {
    private static final Logger LOGGER = LoggerFactory.getLogger(Sender.class);

    @Autowired
    private KafkaTemplate<String, KafkaModelDto> kafkaTemplate;

    public void send(String topic, KafkaModelDto payload) {
        LOGGER.info("sending payload='{}' to topic='{}'", payload, topic);

        Message<KafkaModelDto> message = MessageBuilder
                .withPayload(payload)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .build();

        kafkaTemplate.send(message);
    }
}
