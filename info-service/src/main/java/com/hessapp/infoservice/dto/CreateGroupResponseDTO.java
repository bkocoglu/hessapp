package com.hessapp.infoservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hessapp.infoservice.client.group.dto.Spend;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateGroupResponseDTO {
    private int groupID;
    private String name;
    private String description;
    private String moderator;
    private List<Participant> participants;
    private List<Spend> spends;
    private List<Message> messages;
    private int unaspiringMessageCount;
}
