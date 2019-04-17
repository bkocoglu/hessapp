package com.hassapp.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO {
    private String nickname;
    private String name;
    private String surname;
    private String email;
    private String gender;
    private String photoUrl;
    private int birthYear;
    private List<com.hassapp.api.dto.Group> groups;
}
