package com.hassapp.api.client.group.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Spend {
    private String id;
    private String groupId;
    private String from;
    private String description;
    private Double totalAmount;
    private LocalDateTime date;
}
