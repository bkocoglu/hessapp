package com.hessapp.api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreateSpendRequest {
    @NotNull
    @NotEmpty
    private String groupId;

    @NotNull
    @NotEmpty
    private String from;

    @NotNull
    @NotEmpty
    private String description;

    @NotNull
    @Positive
    private double totalAmount;

}
