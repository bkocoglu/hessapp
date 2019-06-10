package com.hessapp.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupStatusDeptItem {
    private String ActivityId;
    private String From;
    private double Amount;
    private String Description;
}
