package com.hessapp.infoservice.controller;

import com.hessapp.infoservice.assembler.CreateGroupResponseAssembler;
import com.hessapp.infoservice.client.chat.ChatClient;
import com.hessapp.infoservice.client.group.GroupClient;
import com.hessapp.infoservice.client.group.dto.Group;
import com.hessapp.infoservice.dto.*;
import com.hessapp.infoservice.service.UserInfoService;
import com.hessapp.infoservice.providers.MessageProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
@CrossOrigin
@RestController
@RequestMapping(value = GroupController.END_POINT)
public class GroupController {
    static final String END_POINT = "/group";
    static final Logger LOGGER = LoggerFactory.getLogger(GroupController.class);

    private RestTemplate restTemplate;
    private GroupClient groupClient;
    private UserInfoService userInfoService;
    private ChatClient chatClient;

    GroupController(RestTemplate restTemplate,
                    GroupClient groupClient,
                    UserInfoService userInfoService,
                    ChatClient chatClient) {
        this.restTemplate = restTemplate;
        this.userInfoService = userInfoService;
        this.groupClient = groupClient;
        this.chatClient = chatClient;
    }

    @CrossOrigin
    @PostMapping(
            value = "/create",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity createGroup(@RequestBody CreateGroupRequestDTO createGroupRequestDTO){
        Group group = groupClient.createGroup(createGroupRequestDTO);
        if (group == null){
            String message = MessageProvider.groupCreateNotWorking;
            return ResponseEntity
                    .badRequest()
                    .body(
                            GeneralResponseDTO
                                    .builder()
                                    .message(message)
                                    .build()
                    );
        }

        List<String> names = userInfoService.getUserNames(group.getParticipants());

        if (names == null || names.isEmpty()){
            return ResponseEntity
                    .badRequest()
                    .body(
                            GeneralResponseDTO
                                    .builder()
                                    .message(MessageProvider.groupCreateNotWorking)
                                    .build());
        }

        List<Participant> participants = new ArrayList<>();

        for (int i = 0; i<names.size() ; i++) {
            Participant participant = Participant
                    .builder()
                    .nickname(group.getParticipants().get(i))
                    .name(names.get(i))
                    .build();
            participants.add(participant);
        }

        group.setMessages(new ArrayList<>());
        group.setUnaspiringMessageCount(0);

        CreateGroupResponseDTO res = CreateGroupResponseAssembler.convertCreateDTO(group, participants);

        chatClient.createGroupMessage(res);

        return ResponseEntity.ok().body(res);
    }

    @CrossOrigin
    @PostMapping(
            value = "/delete",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity deleteGroup(@RequestBody DeleteGroupRequestDTO deleteGroupRequestDTO){

        DeleteGroupResponseDTO res = groupClient.deleteGroup(deleteGroupRequestDTO);

        if ( res == null ){
            return ResponseEntity.badRequest().body(
                    GeneralResponseDTO
                            .builder()
                            .message(MessageProvider.groupDeleteNotWorking)
                            .build()
            );
        }
        chatClient.deleteGroupMessages(res.getGroupId());
        return ResponseEntity.ok().body(res);
    }
}
