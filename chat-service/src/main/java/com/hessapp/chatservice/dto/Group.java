package com.hessapp.chatservice.dto;

import com.hessapp.chatservice.dao.Message;
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
    private int groupID;
    private String name;
    private String description;
    private String moderator;
    private List<Participant> participants;
    private List<Spend> spends;
    private List<Message> messages;
    private int unaspiringMessageCount;
}
