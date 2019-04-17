package com.hessapp.chatservice.controller;

import com.hessapp.chatservice.dto.ChatSocketResponse;
import com.hessapp.chatservice.dto.Spend;
import com.hessapp.chatservice.enums.SocketMessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.awt.*;

@CrossOrigin
@RestController
@RequestMapping(value = SpendActionController.END_POINT)
public class SpendActionController {
    public static final String END_POINT = "/spend";
    private static final Logger LOGGER = LoggerFactory.getLogger(SpendActionController.class);

    private SimpMessagingTemplate simpMessagingTemplate;

    SpendActionController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @CrossOrigin
    @PostMapping(
            value = "/create",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity createSpendMessage(@RequestBody Spend spend) {
        try {
            LOGGER.info("Create Spend !");

            ChatSocketResponse chatSocketResponse = new ChatSocketResponse();
            chatSocketResponse.setMessageType(SocketMessageType.SPENDCREATE);
            chatSocketResponse.setSpend(spend);

            simpMessagingTemplate.convertAndSend("/chat", chatSocketResponse);

            ResponseEntity.ok().build();
        }catch (Exception e) {
            LOGGER.error(e.toString());
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
