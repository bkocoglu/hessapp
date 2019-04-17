package com.hassapp.api.controller;

import com.hassapp.api.dto.RegisterRequestDTO;
import com.hassapp.api.kafka.dto.KafkaModelDto;
import com.hassapp.api.kafka.producer.Sender;
import com.hassapp.api.model.User;
import com.hassapp.api.service.RegisterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping(value = RegisterController.END_POINT)
@PropertySource("classpath:message.properties")
public class RegisterController {
    public static final String END_POINT = "/register";
    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterController.class);

    private RegisterService registerService;
    private Sender kafkaSender;

    RegisterController(RegisterService registerService,
                       Sender kafkaSender){
        this.registerService = registerService;
        this.kafkaSender = kafkaSender;
    }

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity register(@Valid @RequestBody RegisterRequestDTO registerRequestDTO){

        registerService.alreadyTakenMail(registerRequestDTO.getEmail());


        User user = registerService.createAndSave(registerRequestDTO, "SYSTEM");

        String activationCode = registerService.setActivatedCode(user);

        kafkaSender.send(
                "accountActivateUrl",
                KafkaModelDto
                        .builder()
                        .mail(user.getMail())
                        .activationUrl(activationUrl + "/" + activationCode)
                        .build()
        );

        // success create new user but now get qr code and pin
        LOGGER.info(registerSuccess);

        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "account-activate/{activationCode}")
    public ResponseEntity accountActivate(@PathVariable("activationCode") String activationCode){
        if (activationCode.length() != RegisterService.ACCOUNTCODE_COUNT){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        registerService.accountActivate(activationCode);

        return ResponseEntity.ok().build();
    }

    @Value("${success.register}")
    private String registerSuccess;

    @Value("${kafka.topic.activationUrl}")
    private String activationUrl;
}
