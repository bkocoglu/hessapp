package com.hessapp.infoservice.client.spend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Spend {
    @JsonProperty("GroupID")
    private int groupId;

    @JsonProperty("From")
    private String from;

    @JsonProperty("Description")
    private String description;

    @JsonProperty("TotalAmount")
    private Double totalAmount;

    @JsonProperty("Date")
    private String date;

}
