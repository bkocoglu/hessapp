package com.hessapp.api.controller;

import com.hessapp.api.assembler.GroupAssembler;
import com.hessapp.api.dto.request.CreateGroupRequest;
import com.hessapp.api.dto.GroupPattern;
import com.hessapp.api.dto.request.DeleteGroupRequest;
import com.hessapp.api.dto.response.LoginUserResponse;
import com.hessapp.api.model.Group;
import com.hessapp.api.service.GroupService;
import com.hessapp.api.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = GroupController.END_POINT)
public class GroupController {
    public static final String END_POINT = "/group";
    private static final Logger log = LoggerFactory.getLogger(GroupController.class);

    private LoginService loginService;
    private GroupAssembler groupAssembler;
    private GroupService groupService;

    GroupController(LoginService loginService, GroupAssembler groupAssembler, GroupService groupService) {
        this.loginService = loginService;
        this.groupAssembler = groupAssembler;
        this.groupService = groupService;
    }

    @GetMapping("/{nickname}")
    public ResponseEntity getGroupInformation(@PathVariable("nickname") String nickname) {
        loginService.nicknameControl(nickname);

        List<Group> groups = loginService.getGroupsbyNick(nickname);
        List<GroupPattern> groupPatterns = new ArrayList<>();

        if (!groups.isEmpty()) {
            for (Group group : groups) {
                GroupPattern gp = groupAssembler.convertGroupToPattern(group);
                gp.setSpends(loginService.getSpendbyGroup(group.getId()));
                groupPatterns.add(gp);
            }
        }

        LoginUserResponse loginUserResponse = LoginUserResponse.builder().groups(groupPatterns).build();

        return ResponseEntity.status(HttpStatus.OK).body(loginUserResponse);
    }

    @PostMapping("/create")
    public ResponseEntity createGroup(@Valid @RequestBody CreateGroupRequest createGroupRequest) {
        groupService.createGroup(createGroupRequest);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete")
    public ResponseEntity deleteGroup(@Valid @RequestBody DeleteGroupRequest deleteGroupRequest){
        groupService.deleteGroup(deleteGroupRequest.getGroupId(), deleteGroupRequest.getParticipantId());
        //test edilmeli !
        return ResponseEntity.ok().build();
    }
}
