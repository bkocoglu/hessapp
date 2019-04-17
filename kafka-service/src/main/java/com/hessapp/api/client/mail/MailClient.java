package com.hessapp.api.client.mail;

import com.hessapp.api.client.mail.dto.MailRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
@PropertySource("classpath:message.properties")
public class MailClient {
    private static final Logger LOG = LoggerFactory.getLogger(MailClient.class);


    private RestTemplate restTemplate;

    MailClient(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public void sendMail(MailRequest mailRequest) {
        try {
            URI uri = new URI(baseUrl + sendMailUrl);

            restTemplate.postForEntity(uri, mailRequest, null);

            LOG.info("mail send");
        }catch (Exception e) {
            LOG.error(e.getMessage());
        }
    }

    @Value("${url.base}")
    private String baseUrl;

    @Value("${url.mail.send}")
    private String sendMailUrl;
}
