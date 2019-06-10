package com.hessapp.api.service;

import com.hessapp.api.assembler.GroupAssembler;
import com.hessapp.api.dto.request.CreateGroupRequest;
import com.hessapp.api.exception.DatabaseException;
import com.hessapp.api.exception.NotFoundException;
import com.hessapp.api.model.Group;
import com.hessapp.api.model.Spend;
import com.hessapp.api.repository.ActivityRepository;
import com.hessapp.api.repository.GroupRepository;
import com.hessapp.api.repository.SpendRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@PropertySource("classpath:message.properties")
public class GroupService {
    private static final Logger log = LoggerFactory.getLogger(GroupService.class);

    private GroupAssembler groupAssembler;
    private GroupRepository groupRepository;
    private SpendRepository spendRepository;
    private ActivityRepository activityRepository;

    public GroupService(GroupAssembler groupAssembler,
                        GroupRepository groupRepository,
                        SpendRepository spendRepository,
                        ActivityRepository activityRepository){
        this.groupAssembler = groupAssembler;
        this.groupRepository = groupRepository;
        this.spendRepository = spendRepository;
        this.activityRepository = activityRepository;
    }

    public Group createGroup(CreateGroupRequest createGroupRequest){
        Group group = groupAssembler.convertRequestToGroup(createGroupRequest);
        group.setCreateDate(LocalDateTime.now());

        group = groupRepository.save(group);
        if (group.getId() == null){
            throw new DatabaseException();
        }else
            return group;
    }

    public Group getGroupById(String groupId){
        Optional<Group> groupOpt = groupRepository.findById(groupId);

        if (groupOpt.isPresent()){
            log.info(groupId + " called !");
            return groupOpt.get();
        }else {
            log.error(groupId + " Grup bulunamadı !");
            throw new NotFoundException(groupNotFoundMessage);
        }
    }

    @Transactional
    public void deleteGroup(String groupId, String participantId){
        Group group = getGroupById(groupId);

        if (group.getModerator().equals(participantId)){
            //delete group
            List<Spend> spends = spendRepository.findByGroupId(group.getId());
            if (!spends.isEmpty()){
                for(Spend spend: spends){
                    activityRepository.deleteBySpendId(spend.getId());
                }
                spendRepository.deleteAll(spends);
            }
            groupRepository.deleteById(group.getId());
        }else {
            //exception
            throw new IndexOutOfBoundsException(illegalModerator);
        }
    }

    @Value("${notfound.group}")
    private String groupNotFoundMessage;

    @Value("${illegal.moderator}")
    private String illegalModerator;
}
