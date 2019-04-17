package com.hessapp.chatservice.dto;

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
    //@JsonProperty("SpendID")   dont need spendId this service !
    //private int spendId;
    private int groupId;
    private String from;
    private String description;
    private Double totalAmount;
    private String date;



}
