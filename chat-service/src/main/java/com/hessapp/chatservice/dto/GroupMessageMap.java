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
public class GroupMessageMap {
    private int groupId;
    private List<Message> messages;
    private int unaspiringMessageCount;
}
