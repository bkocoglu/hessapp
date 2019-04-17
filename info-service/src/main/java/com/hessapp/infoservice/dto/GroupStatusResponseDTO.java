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
public class GroupStatusResponseDTO {
    private double totalDebt;
    private double totalCredit;
    private List<DebtsDTO> listDebt;
}
