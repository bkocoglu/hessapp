package com.hassapp.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SocialLoginRequest {
    @NotNull
    private String firstName;
    @NotNull
    private String surname;
    @NotNull
    private String email;
    @NotNull
    private String photoUrl;
    @NotNull
    private String mediaType;
}
