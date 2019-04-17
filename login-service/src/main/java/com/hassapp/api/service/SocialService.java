package com.hassapp.api.service;

import com.hassapp.api.enums.SocialUserType;
import com.hassapp.api.model.User;
import com.hassapp.api.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SocialService {
    static final Logger LOGGER = LoggerFactory.getLogger(SocialService.class);

    private UserRepository userRepository;

    SocialService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public SocialUserType userTypeControl(String mail, String userType){
        List<User> users = userRepository.findByMail(mail);

        if (users.isEmpty()){
            return SocialUserType.NEW;
        }else {
            for (User user: users) {
                if (!user.getUserType().equals(userType)){
                    return SocialUserType.ALLREADYTAKEN;
                }
            }
            return SocialUserType.LAST;
        }
    }

    public User getUser(String mail){
        return userRepository.findByMail(mail).get(0);
    }
}
