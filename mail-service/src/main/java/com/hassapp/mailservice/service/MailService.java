package com.hassapp.mailservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class MailService {
    static final Logger LOGGER = LoggerFactory.getLogger(MailService.class);

    @Autowired
    private JavaMailSender javaMailSender;

    public boolean sendMail(String subject, String destination, String body){
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

            mimeMessageHelper.setTo(destination);
            mimeMessageHelper.setText(body, true);
            mimeMessageHelper.setSubject(subject);

            javaMailSender.send(mimeMessage);

            LOGGER.info("Mail Başarıyla Gönderildi");

            return true;
        }catch (Exception e){
            LOGGER.error(e.toString());
            return false;
        }
    }
}
