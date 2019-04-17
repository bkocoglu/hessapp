package com.hessapp.infoservice.assembler;

import com.hessapp.infoservice.client.group.dto.Group;
import com.hessapp.infoservice.dto.CreateGroupResponseDTO;
import com.hessapp.infoservice.dto.Participant;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CreateGroupResponseAssembler {
    public static CreateGroupResponseDTO convertCreateDTO(Group group, List<Participant> participants) {
        return CreateGroupResponseDTO
                .builder()
                .groupID(group.getGroupID())
                .description(group.getDescription())
                .moderator(group.getModerator())
                .name(group.getName())
                .spends(group.getSpends())
                .participants(participants)
                .messages(group.getMessages())
                .unaspiringMessageCount(group.getUnaspiringMessageCount())
                .build();
    }
}
