package com.hessapp.api.dto;

import com.hessapp.api.model.Spend;
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
public class GroupPattern {
    private String id;
    private String name;
    private String description;
    private String moderator;
    private List<String> participants;
    private List<Spend> spends;
    private LocalDateTime createDate;
    private Boolean isPublic;
}
