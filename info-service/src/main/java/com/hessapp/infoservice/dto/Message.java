package com.hessapp.infoservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message {
    private String id;
    private int groupId;
    private String from;
    private String body;
    private LocalDateTime sendDate;
}
