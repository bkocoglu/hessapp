package com.hassapp.api.dto;

import com.hassapp.api.model.City;
import lombok.Builder;
import lombok.Data;
import org.springframework.context.annotation.PropertySource;

import javax.validation.Valid;
import javax.validation.constraints.*;

@Data
@Builder
@PropertySource("classpath:application.properties")
public class RegisterRequestDTO {
    @NotNull
    private String firstName;

    @NotNull
    private String surName;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(min = 6)
    private String password;

    @NotNull
    @DecimalMin("1950")
    private int birthyear;

    @NotNull
    @Valid
    private City city;

    @NotNull
    private String gender;
}
