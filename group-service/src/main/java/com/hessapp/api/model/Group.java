package com.hessapp.api.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "Group")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@ToString
public class Group {
    @Id
    private String id;
    private String name;
    private String description;
    private String moderator;
    private List<String> participants;
    private LocalDateTime createDate;
    private Boolean isPublic;
}
