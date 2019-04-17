package com.hessapp.api.service;

import com.hessapp.api.exception.DatabaseException;
import com.hessapp.api.exception.IncorrectParamsException;
import com.hessapp.api.model.Group;
import com.hessapp.api.model.Spend;
import com.hessapp.api.repository.GroupRepository;
import com.hessapp.api.repository.SpendRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@PropertySource("classpath:message.properties")
public class LoginService {
    private static final Logger log = LoggerFactory.getLogger(LoginService.class);

    private GroupRepository groupRepository;
    private SpendRepository spendRepository;

    LoginService(GroupRepository groupRepository,
                 SpendRepository spendRepository){
        this.groupRepository = groupRepository;
        this.spendRepository = spendRepository;
    }

    public void nicknameControl(String nickname){
        if (!nickname.substring(0,4).equals("@hs-") || nickname.length() != 10){
            throw new IncorrectParamsException(nicknameFailed);
        }
    }

    public List<Group> getGroupsbyNick(String nickname){
        try {
            List<Group> groups = groupRepository.findAllByParticipantsContains(nickname);

            return groups;
        }catch (Exception ex){
            log.error(ex.getMessage());
            throw new DatabaseException();
        }
    }

    public List<Spend> getSpendbyGroup(String groupId){
        try {
            return spendRepository.findByGroupId(groupId);
        }catch (Exception ex){
            log.error(ex.getMessage());
            throw new DatabaseException();
        }
    }

    @Value("${exception.login.nickname}")
    private String nicknameFailed;
}
