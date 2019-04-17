package com.hassapp.api.service;

import com.hassapp.api.assembler.GroupAssembler;
import com.hassapp.api.client.group.dto.Spend;
import com.hassapp.api.client.message.dto.GroupMessageMap;
import com.hassapp.api.client.message.dto.Message;
import com.hassapp.api.dto.Group;
import com.hassapp.api.dto.LoginResponseDTO;
import com.hassapp.api.dto.Participant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupSettingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupSettingService.class);

    private InfoService infoService;

    GroupSettingService(InfoService infoService) {
        this.infoService = infoService;
    }

    public List<Group> changeParticipant(List<com.hassapp.api.client.group.dto.Group> groups) {

        List<Group> newGroups = new ArrayList<>();

        for (com.hassapp.api.client.group.dto.Group group: groups) {
            Group newGroup = GroupAssembler.convertGroupToViewGroup(group);
            List<Participant> participants = new ArrayList<>();
            List<String> nicknames = new ArrayList<>();

            for (String participant: group.getParticipants()) {
                nicknames.add(participant);
                Participant part = new Participant();
                part.setNickname(participant);
                participants.add(part);
            }
            List<String> names = infoService.nicknamesConvertNames(nicknames);

            for (int i=0; i<participants.size();i++) {
                participants.get(i).setName(names.get(i));
            }

            newGroup.setParticipants(participants);

            if(!newGroup.getSpends().isEmpty()) {
                List<String> spendNicks = new ArrayList<>();
                for (Spend spend: newGroup.getSpends()){
                    spendNicks.add(spend.getFrom());
                    spend.setFrom(infoService.nicknamesConvertNames(spendNicks).get(0));
                    spendNicks.remove(0);
                }
            }

            newGroups.add(newGroup);
        }

        return newGroups;
    }

    public LoginResponseDTO setMessageEmpty(LoginResponseDTO loginResponseDTO){
        for (Group group: loginResponseDTO.getGroups()) {
            group.setMessages(new ArrayList<>());
            group.setUnaspiringMessageCount(0);
        }

        return loginResponseDTO;
    }

    public LoginResponseDTO messageToGroupMap(LoginResponseDTO loginResponseDTO, List<GroupMessageMap> groupMessageMaps) {
        for (Group group: loginResponseDTO.getGroups()) {
            for (GroupMessageMap grpMsgMap: groupMessageMaps) {
                if (group.getGroupID().equals(grpMsgMap.getGroupId())) {
                    group.setUnaspiringMessageCount(grpMsgMap.getUnaspiringMessageCount());
                    group.setMessages(grpMsgMap.getMessages());
                }
            }
        }
        //return changeMessageFromName(loginResponseDTO);
        return loginResponseDTO;
    }

    public LoginResponseDTO changeMessageFromName(LoginResponseDTO loginResponseDTO) {
        for (Group grp: loginResponseDTO.getGroups()) {
            for (Message msg: grp.getMessages()) {
                msg.setFrom(convertNickToName(msg.getFrom(), grp.getParticipants()));
                msg.setSendDate(msg.getSendDate().minusHours(2));
            }
        }
        return loginResponseDTO;
    }

    private String convertNickToName(String nickname, List<Participant> participants) {
        for (Participant participant: participants) {
            if (participant.getNickname().equals(nickname))
                return participant.getName();
        }
        return nickname;
    }

}
