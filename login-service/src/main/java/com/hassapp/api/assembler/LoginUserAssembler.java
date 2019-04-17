package com.hassapp.api.assembler;

import com.hassapp.api.dto.LoginResponseDTO;
import com.hassapp.api.model.User;
import org.springframework.stereotype.Component;

@Component
public class LoginUserAssembler {
    public LoginResponseDTO createLoginResponse(User user){
        return LoginResponseDTO.builder()
                .email(user.getMail())
                .gender(user.getGender())
                .name(user.getName())
                .nickname(user.getNicname())
                .photoUrl(user.getPhotoUrl())
                .surname(user.getSurname())
                .birthYear(user.getBirthYear())
                .build();
    }
}
