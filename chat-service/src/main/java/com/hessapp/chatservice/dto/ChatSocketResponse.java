package com.hessapp.chatservice.dto;

import com.hessapp.chatservice.dao.Message;
import com.hessapp.chatservice.enums.SocketMessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatSocketResponse {
    private SocketMessageType messageType;
    private Message message;
    private int groupId;
    private Group group;
    private Spend spend;
}
