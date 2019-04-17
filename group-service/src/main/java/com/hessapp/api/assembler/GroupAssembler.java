package com.hessapp.api.assembler;

import com.hessapp.api.dto.GroupPattern;
import com.hessapp.api.dto.request.CreateGroupRequest;
import com.hessapp.api.model.Group;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
public class GroupAssembler {
    public GroupPattern convertGroupToPattern(Group group){
        return GroupPattern
                .builder()
                .id(group.getId())
                .name(group.getName())
                .description(group.getDescription())
                .moderator(group.getModerator())
                .participants(group.getParticipants())
                .createDate(group.getCreateDate())
                .isPublic(group.getIsPublic())
                .build();
    }

    public Group convertRequestToGroup(CreateGroupRequest createGroupRequest){
        return Group
                .builder()
                .name(createGroupRequest.getName())
                .description(createGroupRequest.getDescription())
                .moderator(createGroupRequest.getModerator())
                .isPublic(createGroupRequest.getIsPublic())
                .participants(createGroupRequest.getParticipants())
                .build();
    }
}
