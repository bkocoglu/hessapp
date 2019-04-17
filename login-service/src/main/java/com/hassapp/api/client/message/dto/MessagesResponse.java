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
public class MessagesResponse {
    private boolean isOnlineUser;
    private List<GroupMessageMap> groupMessageMaps;
}
