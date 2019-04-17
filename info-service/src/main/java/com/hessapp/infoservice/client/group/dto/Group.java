package com.hessapp.infoservice.client.group.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hessapp.infoservice.client.group.dto.Spend;
import com.hessapp.infoservice.dto.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Group {
    @JsonProperty("groupID")
    private int groupID;
    private String name;
    private String description;
    private String moderator;
    private List<String> participants;
    private List<Spend> spends;
    private List<Message> messages;
    private int unaspiringMessageCount;
}
