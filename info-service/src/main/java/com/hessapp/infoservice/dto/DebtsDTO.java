package com.hessapp.infoservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DebtsDTO {
    @JsonProperty("ActivityId")
    private int activityId;

    @JsonProperty("From")
    private String from;

    @JsonProperty("Amount")
    private double amount;

    @JsonProperty("Description")
    private String description;
}
