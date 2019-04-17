package com.hessapp.chatservice.service;

import com.hessapp.chatservice.dao.Message;
import com.hessapp.chatservice.dto.OnlineSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

@Service
public class GetMessageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetMessageService.class);

    private OnlineUserService onlineUserService;

    GetMessageService(OnlineUserService onlineUserService) {
        this.onlineUserService = onlineUserService;
    }

    public boolean isOnlineUser(String nickname) {
        Iterator<OnlineSession> onlineUsers = OnlineUserService.getOnlineUserList().iterator();
        boolean result = false;

        while (onlineUsers.hasNext()) {
            OnlineSession onlineSession = onlineUsers.next();
            if (onlineSession.getNickname().equals(nickname))
                result = true;
        }

        return result;
    }

    public int calculateUnaspiringMessage(List<Message> messages, LocalDateTime lastSeen) {
        int count = 0;
        for (Message msg: messages) {
            if (lastSeen.isBefore(msg.getSendDate()))
                count++;
        }
        return count;
    }
}
