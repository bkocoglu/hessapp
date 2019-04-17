package com.hessapp.api.dto.request;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreateGroupRequest {
    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private String moderator;

    @NotNull
    private Boolean isPublic;

    @NotNull
    private List<String> participants;
}
