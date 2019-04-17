package com.hessapp.chatservice.controller;

import com.hessapp.chatservice.dao.LastSeen;
import com.hessapp.chatservice.dao.Message;
import com.hessapp.chatservice.dto.GeneralResponse;
import com.hessapp.chatservice.dto.GroupMessageMap;
import com.hessapp.chatservice.dto.LoginUserMessageReq;
import com.hessapp.chatservice.dto.LoginUserMessageRes;
import com.hessapp.chatservice.repositoriy.LastSeenRepository;
import com.hessapp.chatservice.repositoriy.MessageRepository;
import com.hessapp.chatservice.service.GetMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = MessageController.EDN_POINT)
public class MessageController {
    public static final String EDN_POINT = "/messages";
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);

    private MessageRepository messageRepository;
    private LastSeenRepository lastSeenRepository;
    private GetMessageService messageService;

    MessageController(MessageRepository messageRepository,
                      LastSeenRepository lastSeenRepository,
                      GetMessageService getMessageService){
        this.messageRepository = messageRepository;
        this.lastSeenRepository = lastSeenRepository;
        this.messageService = getMessageService;
    }

    @CrossOrigin
    @PostMapping(
            value = "/user/groups",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity getUserMessages(@RequestBody LoginUserMessageReq loginUserMessageReq) {
        //kişinin ait olduğu bir grup yoksa buraya istek atılmamalı
        LOGGER.info("İstek buraya geldi !!");
        //kişi o anda zaten online ise yeni bir bağlantıya izin verilmez.
        if (messageService.isOnlineUser(loginUserMessageReq.getNickname())){
            return ResponseEntity.ok(
                    LoginUserMessageRes
                            .builder()
                            .isOnlineUser(true)
                            .groupMessageMaps(null)
                            .build()
            );
        }

        LoginUserMessageRes res = new LoginUserMessageRes();
        res.setOnlineUser(false);

        List<GroupMessageMap> groupMessageMaps = new ArrayList<>();

        List<Integer> groupIds = loginUserMessageReq.getGroupIdList();

        for (int groupId: groupIds) {
            GroupMessageMap groupMessageMap = new GroupMessageMap();
            groupMessageMap.setGroupId(groupId);

            List<Message> messages = messageRepository.findByGroupIdOrderBySendDate(groupId);
            groupMessageMap.setMessages(messages);

            //grupta daha önce mesajlaşma olmamışsa okunmamış mesajlar hesaplanmaz.
            if (messages == null || messages.isEmpty()) {
                groupMessageMap.setUnaspiringMessageCount(0);
            } else {
                List<LastSeen> lastSeens = lastSeenRepository.findByNickname(loginUserMessageReq.getNickname());

                //kullanıcı daha önce hiç giriş yapmamışsa tüm mesajlar okunmamıştır.
                if (lastSeens.isEmpty()) {
                    groupMessageMap.setUnaspiringMessageCount(messages.size());
                } else {
                    LastSeen lastSeen = lastSeens.get(0);
                    int unaspiringCount = messageService.calculateUnaspiringMessage(messages,lastSeen.getLastSeen());
                    groupMessageMap.setUnaspiringMessageCount(unaspiringCount);
                }
            }

            groupMessageMaps.add(groupMessageMap);
        }

        res.setGroupMessageMaps(groupMessageMaps);

        return ResponseEntity.ok(res);
    }

    /*
    @CrossOrigin
    @GetMapping()
    public ResponseEntity deneme(){
        Message message = new Message(1020,"deneme","deneme", LocalDateTime.now());
        LastSeen lastSeen = new LastSeen("deneme", LocalDateTime.now());

        lastSeenRepository.save(lastSeen);
        messageRepository.save(message);

        return ResponseEntity.ok().build();
    }
    */

}
