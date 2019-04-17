package com.hassapp.api.assembler;

import com.hassapp.api.dto.Group;
import org.springframework.stereotype.Component;

@Component
public class GroupAssembler {
    public static Group convertGroupToViewGroup(com.hassapp.api.client.group.dto.Group group) {
        Group newGroup = new Group();
        newGroup.setGroupID(group.getId());
        newGroup.setDescription(group.getDescription());
        newGroup.setModerator(group.getModerator());
        newGroup.setName(group.getName());
        newGroup.setSpends(group.getSpends());

        return newGroup;
    }
}
