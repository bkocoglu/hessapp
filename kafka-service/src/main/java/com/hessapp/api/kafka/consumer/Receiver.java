package com.hessapp.api.kafka.consumer;

import com.hessapp.api.client.enums.MailType;
import com.hessapp.api.client.mail.MailClient;
import com.hessapp.api.client.mail.dto.MailRequest;
import com.hessapp.api.kafka.dto.KafkaModelDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class Receiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    private MailClient mailClient;

    Receiver(MailClient mailClient) {
        this.mailClient = mailClient;
    }

    @KafkaListener(topics = "accountActivateUrl")
    public void receive(@Payload KafkaModelDto kafkaModelDto,
                        @Headers MessageHeaders headers) {
        LOGGER.info("received data='{}'", kafkaModelDto.toString());

        mailClient.sendMail(
                MailRequest
                        .builder()
                        .activationUrl(kafkaModelDto.getActivationUrl())
                        .type(MailType.ACCOUNT_ACTIVATE)
                        .destinationMail(kafkaModelDto.getMail())
                        .build()
        );
    }

    @KafkaListener(topics = "resetPassword")
    public void reciveResetPass(@Payload KafkaModelDto kafkaModelDto,
                                @Headers MessageHeaders headers) {
        LOGGER.info("received data='{}'", kafkaModelDto.toString());

        mailClient.sendMail(
                MailRequest
                        .builder()
                        .type(MailType.PASSWORD_RESET)
                        .destinationMail(kafkaModelDto.getMail())
                        .newPassword(kafkaModelDto.getNewPass())
                        .build()
        );
    }
}
