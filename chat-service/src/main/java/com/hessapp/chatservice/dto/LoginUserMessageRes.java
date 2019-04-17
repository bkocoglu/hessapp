package com.hessapp.chatservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginUserMessageRes {
    private boolean isOnlineUser;
    private List<GroupMessageMap> groupMessageMaps;
}
