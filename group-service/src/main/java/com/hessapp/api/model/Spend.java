package com.hessapp.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

@Document(collection = "Spend")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Spend {
    @Id
    private String id;
    private String groupId;
    private String from;
    private String description;
    private Double totalAmount;
    private LocalDateTime date;
}
