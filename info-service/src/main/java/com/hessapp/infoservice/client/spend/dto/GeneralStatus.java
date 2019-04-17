package com.hessapp.infoservice.client.spend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GeneralStatus {
    private int groupId;
    private String groupName;
    private double totalCredit;
    private double totalDebt;
}
