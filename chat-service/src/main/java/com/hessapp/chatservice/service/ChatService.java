package com.hessapp.chatservice.service;

import com.hessapp.chatservice.dao.Message;
import com.hessapp.chatservice.repositoriy.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ChatService {
    static final Logger LOGGER = LoggerFactory.getLogger(ChatService.class);

    private MessageRepository messageRepository;

    ChatService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message createMessage(String message, String from, int group) {
        Message msg = new Message();
        msg.setBody(message);
        msg.setGroupId(group);
        msg.setFrom(from);
        msg.setSendDate(LocalDateTime.now().plusHours(3));
        return msg;
    }

    public void deleteAllMessage(int groupId) {
        try {
            messageRepository.deleteAllByGroupId(groupId);
        }catch (Exception e) {
            LOGGER.error(e.toString());
        }
    }
}
