package com.hassapp.mailservice.dto;

import com.hassapp.mailservice.enums.MailType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MailRequest {
    private String destinationMail;
    private String newPassword;
    private String nameAndSurname;
    private MailType type;
    private String activationUrl;
}
