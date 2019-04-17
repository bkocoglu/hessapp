package com.hessapp.infoservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateGroupRequestDTO {
    private String groupName;
    private String description;
    private String moderator;
    private List<String> participants;
}
