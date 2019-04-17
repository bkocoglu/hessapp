package com.hassapp.api.controller;


import com.hassapp.api.dto.*;
import com.hassapp.api.enums.SocialUserType;
import com.hassapp.api.enums.RandomType;
import com.hassapp.api.kafka.dto.KafkaModelDto;
import com.hassapp.api.kafka.producer.Sender;
import com.hassapp.api.model.User;
import com.hassapp.api.service.*;
import com.hassapp.api.providers.RandomPinProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = LoginController.END_POINT)
@PropertySource("classpath:message.properties")
public class LoginController {
    static final String END_POINT = "/login";
    static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
    static final int PASS_COUNT = 8;


    private RegisterService registerService;
    private LoginService loginService;
    private SocialService socialService;
    private PasswordResetService passwordResetService;
    private InfoService infoService;
    private Sender kafkaSender;
    /*
    @Autowired
    MailClient mailClient;
*/

    LoginController(RegisterService registerService,
                    LoginService loginService,
                    SocialService socialService,
                    PasswordResetService passwordResetService,
                    InfoService infoService,
                    Sender kafkaSender){
        this.registerService = registerService;
        this.loginService = loginService;
        this.socialService = socialService;
        this.passwordResetService = passwordResetService;
        this.infoService = infoService;
        this.kafkaSender = kafkaSender;
    }


    @CrossOrigin
    @PostMapping(
            value = "/social",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity fbLogin(@Valid @RequestBody SocialLoginRequest socialLoginRequest){

        SocialUserType socialUserType = socialService.userTypeControl(socialLoginRequest.getEmail(), socialLoginRequest.getMediaType());

        if (socialUserType == SocialUserType.NEW){
            // detect new user

            User user = registerService.createAndSave(RegisterRequestDTO.builder()
                    .firstName(socialLoginRequest.getFirstName())
                    .surName(socialLoginRequest.getSurname())
                    .email(socialLoginRequest.getEmail())
                    .build(),
                    socialLoginRequest.getMediaType()
            );

            // create new fb user and redirect /hessapp in angular

            LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
            loginResponseDTO.setNickname(user.getNicname());
            loginResponseDTO.setName(user.getName());
            loginResponseDTO.setGroups(new ArrayList<>());

            return ResponseEntity.ok().body(loginResponseDTO);

        }

        else if (socialUserType == SocialUserType.LAST){
            User user = socialService.getUser(socialLoginRequest.getEmail());

            LoginResponseDTO loginResponseDTO = loginService.loginUserInfo(user);

            LOGGER.info(user.getNicname() + " Login Success");
            return ResponseEntity.ok().body(loginResponseDTO);
        }

        else if (socialUserType == SocialUserType.ALLREADYTAKEN){
            LOGGER.error(socialAlreadyToken);
            return ResponseEntity.badRequest().body(
                    GeneralResponseDTO.builder()
                            .message(socialAlreadyToken)
                            .build()
            );
        }

        else {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin
    @PostMapping(
            value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity login(@Valid @RequestBody LoginRequestDTO loginRequestDTO){

        User user = loginService.findUser(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());

        if (user!=null){
            LoginResponseDTO loginResponseDTO = loginService.loginUserInfo(user);

            LOGGER.info(user.getNicname() + " Giris basarili !");
            return ResponseEntity.ok().body(loginResponseDTO);
        }else {
            // not found user
            LOGGER.error("Mail adresi veya şifre hatalı !");
            return ResponseEntity.notFound().build();
        }
    }


    @CrossOrigin
    @PostMapping(
            value = "/resetPassword",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity resetPass(@Valid @RequestBody ResetPasswordRequestDTO resetPasswordRequestDTO){

        User user = passwordResetService.userControl(resetPasswordRequestDTO.getEmail());

        String newPassword = RandomPinProvider.createRandomPin(RandomType.PASSWORD, PASS_COUNT);

        passwordResetService.changePassword(user, newPassword);

        kafkaSender.send(
                "resetPassword",
                KafkaModelDto
                        .builder()
                        .mail(user.getMail())
                        .newPass(newPassword)
                        .build()
        );

        return ResponseEntity.ok().build();
    }

    @CrossOrigin
    @PostMapping(
            value = "/namesInfo",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity getUsersName(@RequestBody UserNamesDTO nicnames){
        List<String> names = infoService.nicknamesConvertNames(nicnames.getNicnames());

        if (names == null){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().body(UserNamesDTO.builder().nicnames(names).build());
    }

    @Value("${success.register}")
    private String registerSuccess;

    @Value("${kafka.topic.activationUrl}")
    private String activationUrl;

    @Value("${exception.login.social.alreadyToken}")
    private String socialAlreadyToken;


}
