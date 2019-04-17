package com.hassapp.api.client.tfa.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TFAQRResponseDTO {
    private String qrurl;
    private String key;
}
