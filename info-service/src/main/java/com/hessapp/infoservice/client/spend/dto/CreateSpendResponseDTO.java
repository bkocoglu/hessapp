package com.hessapp.infoservice.client.spend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateSpendResponseDTO {
    private int groupId;
    private String from;
    private String description;
    private double totalAmount;
    private String date;
}
