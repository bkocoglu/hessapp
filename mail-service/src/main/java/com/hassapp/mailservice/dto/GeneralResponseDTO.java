package com.hassapp.mailservice.dto;

import com.hassapp.mailservice.enums.MailType;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GeneralResponseDTO {
    private String message;
    private MailType type;
}
