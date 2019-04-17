package com.hassapp.api.service;

import com.hassapp.api.client.tfa.dto.TFAuthRequestDTO;
import com.hassapp.api.dto.SocialLoginRequest;
import com.hassapp.api.dto.LoginRequestDTO;
import com.hassapp.api.dto.ResetPasswordRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ValidationService {
    static final Logger LOGGER = LoggerFactory.getLogger(ValidationService.class);


    public boolean validateFacebook(SocialLoginRequest socialLoginRequest){
        boolean validate = socialLoginRequest.getEmail() != null && !(socialLoginRequest.getEmail().equals(""))
                && socialLoginRequest.getFirstName() != null && !(socialLoginRequest.getFirstName().equals(""))
                && socialLoginRequest.getSurname() != null && !(socialLoginRequest.getSurname().equals(""));

        return validate;
    }

    public boolean validationLogin(LoginRequestDTO loginRequestDTO){
        boolean validate = loginRequestDTO.getEmail() != null && !(loginRequestDTO.getEmail().equals(""))
                && loginRequestDTO.getPassword() != null && !(loginRequestDTO.getPassword().equals(""));

        return validate;
    }

    public boolean validationResetPassword(ResetPasswordRequestDTO resetPasswordRequestDTO){
        boolean validate = resetPasswordRequestDTO.getEmail() != null && !(resetPasswordRequestDTO.getEmail().equals(""));

        return validate;
    }

    public boolean validationTFAuth(TFAuthRequestDTO tfAuthRequestDTO){
        boolean validate = tfAuthRequestDTO.getNickname() != null && !(tfAuthRequestDTO.getNickname().equals(""))
                && tfAuthRequestDTO.getPin() != null && !(tfAuthRequestDTO.getPin().equals(""));

        return validate;
    }
}
