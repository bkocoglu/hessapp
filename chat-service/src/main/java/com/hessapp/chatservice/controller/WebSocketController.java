package com.hessapp.chatservice.controller;

import com.google.gson.Gson;
import com.hessapp.chatservice.dao.Message;
import com.hessapp.chatservice.dto.ChatSocketResponse;
import com.hessapp.chatservice.dto.ConnectedNativeHeadersDTO;
import com.hessapp.chatservice.enums.SocketMessageType;
import com.hessapp.chatservice.repositoriy.MessageRepository;
import com.hessapp.chatservice.service.ChatService;
import com.hessapp.chatservice.service.LastSeenService;
import com.hessapp.chatservice.service.OnlineUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Controller
public class WebSocketController implements ApplicationListener<SessionConnectEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketController.class);

    private final SimpMessagingTemplate simpMessagingTemplate;
    private LastSeenService lastSeenService;
    private ChatService chatService;
    private MessageRepository messageRepository;


    WebSocketController(SimpMessagingTemplate simpMessagingTemplate,
                        LastSeenService lastSeenService,
                        ChatService chatService,
                        MessageRepository messageRepository){
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.lastSeenService = lastSeenService;
        this.chatService = chatService;
        this.messageRepository = messageRepository;
    }

    private Gson gson = new Gson();

    @MessageMapping(value = "/send/message")
    public void onReceivedMessage(@Payload String message, MessageHeaders messageHeaders) {
        LinkedMultiValueMap linkedMultiValueMap = messageHeaders.get("nativeHeaders", LinkedMultiValueMap.class);
        String messageFrom = (String)linkedMultiValueMap.get("from").get(0);
        int messageGroup = Integer.parseInt((String)linkedMultiValueMap.get("group").get(0));

        if (messageFrom != null || !messageFrom.equals(" ") || message != null || message.equals(" ") || messageGroup>0) {
            LOGGER.info(message);
            LOGGER.info(messageFrom);
            System.out.println(messageGroup);

            Message msg = chatService.createMessage(message, messageFrom, messageGroup);
            messageRepository.save(msg);

            msg.setSendDate(msg.getSendDate().minusHours(2));

            simpMessagingTemplate.convertAndSend(
                    "/chat",
                    ChatSocketResponse
                            .builder()
                            .messageType(SocketMessageType.MESSAGE)
                            .message(msg)
                            .build()
            );
        }
    }

    @Override
    public void onApplicationEvent(SessionConnectEvent event) {
        LOGGER.info("Session Connect ! Event => " + event.toString());
        ConnectedNativeHeadersDTO connectedNativeHeadersDTO = gson.fromJson(event.getMessage().getHeaders().get("nativeHeaders").toString(),ConnectedNativeHeadersDTO.class);
        String sessionId = event.getMessage().getHeaders().get("simpSessionId").toString();

        //add online list
        OnlineUserService.addSession(sessionId, connectedNativeHeadersDTO.getNickname().get(0));
    }

    @EventListener
    public void onSocketDisconnected(SessionDisconnectEvent event) {
        LOGGER.info("Session Disconnect ! Event => " + event.toString());

        String sessionId = event.getSessionId();

        String nickname = OnlineUserService.findNameBySessionId(sessionId);

        OnlineUserService.removeSession(sessionId);
        lastSeenService.changeLastSeen(nickname);
    }
}
