package com.hassapp.api.assembler;

import com.hassapp.api.client.mail.dto.PasswordResetMailRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class PassResetReqDTOAssembler {
    public PasswordResetMailRequestDTO convertPassResetMailResq(String mail, String password, String name){
        String[] mails = {mail};
        return PasswordResetMailRequestDTO
                .builder()
                .nameAndSurname(name)
                .newPassword(password)
                .destinationMail(mails)
                .build();
    }
}
