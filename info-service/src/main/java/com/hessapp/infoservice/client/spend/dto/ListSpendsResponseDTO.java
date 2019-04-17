package com.hessapp.infoservice.client.spend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListSpendsResponseDTO {
    private List<Spend> spends;
}
