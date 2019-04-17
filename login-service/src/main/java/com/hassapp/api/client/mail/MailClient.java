package com.hassapp.api.client.mail;

import com.hassapp.api.client.mail.dto.PasswordResetMailRequestDTO;
import com.hassapp.api.dto.GeneralResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("mail-service")
public interface MailClient {

    @PostMapping(
            value = "/mail/deneme",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<GeneralResponseDTO> deneme(GeneralResponseDTO generalResponseDTO);


    @PostMapping(
            value = "/mail/passwordReset",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<GeneralResponseDTO> sendPasswordResetMail(PasswordResetMailRequestDTO passwordResetMailRequestDTO);

}
