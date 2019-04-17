package com.hessapp.chatservice.service;

import com.hessapp.chatservice.dto.OnlineSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;

@Service
public class OnlineUserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OnlineUserService.class);

    private static ArrayList<OnlineSession> onlineUserList = new ArrayList<>();

    public static void addSession(String sessionId, String nickname){
        OnlineSession session = new OnlineSession(sessionId, nickname);

        onlineUserList.add(session);
    }

    public static void removeSession(String sessionId) {
        Iterator<OnlineSession> cloneOnline = onlineUserList.iterator();

        while (cloneOnline.hasNext()) {
            OnlineSession session = cloneOnline.next();
            if (session.getSessionId() == sessionId) {
                onlineUserList.remove(session);

                return;
            }
        }
    }

    public static String findNameBySessionId(String sessionId) {
        for (OnlineSession session: onlineUserList) {
            if (session.getSessionId().equals(sessionId)){
                return session.getNickname();
            }
        }
        return null;
    }

    public static ArrayList<OnlineSession> getOnlineUserList() {
        return onlineUserList;
    }

    public static void setOnlineUserList(ArrayList<OnlineSession> onlineUserList) {
        OnlineUserService.onlineUserList = onlineUserList;
    }
}
