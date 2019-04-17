package com.hessapp.chatservice.controller;

import com.hessapp.chatservice.dto.ChatSocketResponse;
import com.hessapp.chatservice.dto.Group;
import com.hessapp.chatservice.enums.SocketMessageType;
import com.hessapp.chatservice.service.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = GroupActionController.END_POINT)
public class GroupActionController {
    public static final String END_POINT = "/group";
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupActionController.class);

    private ChatService chatService;
    private SimpMessagingTemplate simpMessagingTemplate;

    GroupActionController(ChatService chatService,
                          SimpMessagingTemplate simpMessagingTemplate) {
        this.chatService = chatService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @CrossOrigin
    @DeleteMapping(
            value = "/{groupId}/delete"
    )
    public ResponseEntity deleteGroupMessages(@PathVariable("groupId") int groupId) {
        try {
            LOGGER.info("Delete Group " + groupId);
            if (groupId > 0) {
                chatService.deleteAllMessage(groupId);

                ChatSocketResponse chatSocketResponse = new ChatSocketResponse();
                chatSocketResponse.setMessageType(SocketMessageType.GROUPDELETE);
                chatSocketResponse.setGroupId(groupId);
                LOGGER.info("grup silme mesajı gönderildi !");
                simpMessagingTemplate.convertAndSend("/chat", chatSocketResponse);
                return ResponseEntity.ok().build();
            }
        } catch (Exception e) {
            LOGGER.error(e.toString());
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @CrossOrigin
    @PostMapping(
            value = "/create",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity createGroupMessage(@RequestBody Group group) {
        try {
            LOGGER.info("Create Group " + group.getGroupID());

            ChatSocketResponse chatSocketResponse = new ChatSocketResponse();
            chatSocketResponse.setMessageType(SocketMessageType.GROUPCREATE);
            chatSocketResponse.setGroup(group);

            simpMessagingTemplate.convertAndSend("/chat", chatSocketResponse);

            return ResponseEntity.ok().build();
        }catch (Exception e) {
            LOGGER.error(e.toString());
            return ResponseEntity.badRequest().build();
        }
    }
}
