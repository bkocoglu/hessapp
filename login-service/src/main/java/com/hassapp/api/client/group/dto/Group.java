package com.hassapp.api.client.group.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Group {
    private String id;
    private String name;
    private String description;
    private String moderator;
    private List<String> participants;
    private List<Spend> spends;
    private LocalDateTime createDate;
}
