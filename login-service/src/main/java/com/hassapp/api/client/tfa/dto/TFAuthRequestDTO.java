package com.hassapp.api.client.tfa.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TFAuthRequestDTO {
    private String nickname;
    private String pin;
}
