package com.hassapp.api.client.message.dto;

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
    private String groupId;
    private List<Message> messages;
    private int unaspiringMessageCount;
}
