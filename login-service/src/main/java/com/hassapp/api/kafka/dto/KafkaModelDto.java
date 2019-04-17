package com.hassapp.api.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KafkaModelDto {
    private String mail;
    private String activationUrl;
    private String newPass;
}
