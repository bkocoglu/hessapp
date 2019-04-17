package com.hassapp.api.client.mail.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PasswordResetMailRequestDTO {
    private String[] destinationMail;
    private String newPassword;
    private String nameAndSurname;
}
