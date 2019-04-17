package com.hessapp.api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteGroupRequest {
    @NotEmpty
    @NotNull
    private String groupId;

    @NotEmpty
    @NotNull
    private String participantId;
}
