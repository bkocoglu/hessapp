package com.hessapp.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "Activity")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Activity {
    @Id
    private String id;
    private String spendId;
    private String from;
    private String destination;
    private double amount;
    private LocalDateTime date;
}
