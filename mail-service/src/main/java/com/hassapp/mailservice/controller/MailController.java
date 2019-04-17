package com.hassapp.mailservice.controller;

import com.hassapp.mailservice.dto.GeneralResponseDTO;
import com.hassapp.mailservice.dto.MailRequest;
import com.hassapp.mailservice.enums.MailType;
import com.hassapp.mailservice.service.MailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.*;
import sun.nio.cs.UTF_8;
import sun.nio.cs.UnicodeEncoder;


import javax.mail.MessagingException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = MailController.END_POINT)
@PropertySource(value = "classpath:message.properties", encoding = "UTF-8")
public class MailController {
    static final String END_POINT = "/mail";
    private static final Logger LOGGER = LoggerFactory.getLogger(MailController.class);


    private MailService mailService;
    private Configuration freemarkerConfig;

    MailController(MailService mailService,
                   Configuration freemarkerConfig){
        this.mailService = mailService;
        this.freemarkerConfig = freemarkerConfig;
    }


    @CrossOrigin
    @PostMapping(
            value = "/sendMail",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<GeneralResponseDTO> sendMail(@RequestBody MailRequest mailRequest) throws MessagingException, IOException, TemplateException {
        LOGGER.info(mailRequest.getDestinationMail());

        String subject = "";
        Map model = new HashMap();
        Template template = null;
        String body = "";

        if (mailRequest.getType() == MailType.ACCOUNT_ACTIVATE){
            subject = accountActivateSubject;
            model.put("title", accountActiveFooter);
            model.put("activationUrl", mailRequest.getActivationUrl());
            model.put("footerMessage", accountActiveFooter);

            template = freemarkerConfig.getTemplate("account-activate-mail.ftl");
            body = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

        } else if (mailRequest.getType() == MailType.PASSWORD_RESET) {
            subject = passwordResetSubject;
            model.put("title", passwordResetTitle);
            model.put("password", mailRequest.getNewPassword());
            model.put("footerMessage", passwordResetFooter);

            template = freemarkerConfig.getTemplate("reset-password-mail.ftl");
            body = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        }



        boolean isSendMail = mailService.sendMail(subject, mailRequest.getDestinationMail(), body);

        if (isSendMail){
            LOGGER.info("mail gönderme işlemi başarılı !");
            return ResponseEntity.ok().body(
                    GeneralResponseDTO
                            .builder()
                            .message("deneme")
                            .build()
            );
        }else {
            return ResponseEntity.badRequest().body(
                    GeneralResponseDTO
                            .builder()
                            .message("deneme")
                            .build()
            );
        }
    }


    @Value("${mail.subject.password.reset}")
    private String passwordResetSubject;

    @Value("${mail.subject.account.activate}")
    private String accountActivateSubject;

    @Value("${mail.model.title.account.active}")
    private String accountActiveTitle;

    @Value("${mail.model.title.password.reset}")
    private String passwordResetTitle;

    @Value("${mail.model.footer.account.active}")
    private String accountActiveFooter;

    @Value("${mail.model.footer.password.reset}")
    private String passwordResetFooter;
}
