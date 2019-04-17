package com.hassapp.api.assembler;

import com.hassapp.api.dto.RegisterRequestDTO;
import com.hassapp.api.model.User;
import org.springframework.stereotype.Component;

@Component
public class RegisterDTOAssembler {
    public User convertRegisterDTOtoUser(RegisterRequestDTO registerRequestDTO, String nicname, String userType){
        User user = new User();
        user.setNicname(nicname);
        user.setName(registerRequestDTO.getFirstName());
        user.setSurname(registerRequestDTO.getSurName());
        user.setMail(registerRequestDTO.getEmail());
        user.setUserType(userType);
        user.setPassword(registerRequestDTO.getPassword());
        user.setBirthYear(registerRequestDTO.getBirthyear());
        user.setGender(registerRequestDTO.getGender());
        user.setCity(registerRequestDTO.getCity());
        user.setActive(false);
        return user;
    }
}
