package com.hassapp.api.service;

import com.hassapp.api.exceptions.DatabaseException;
import com.hassapp.api.exceptions.UserNotFoundException;
import com.hassapp.api.model.User;
import com.hassapp.api.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@PropertySource("classpath:message.properties")
public class PasswordResetService {
    static final Logger LOGGER = LoggerFactory.getLogger(PasswordResetService.class);

    private UserRepository userRepository;

    PasswordResetService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User userControl(String email){
        List<User> users = userRepository.findByMail(email);
        if(users.isEmpty()){
            LOGGER.info(userNotFound);
            throw new UserNotFoundException(userNotFound);
        }
        if (users.get(0).getPassword() == null){
            LOGGER.info(userSocialAccount);
            throw new UserNotFoundException(userSocialAccount);
        }
        return users.get(0);
    }

    public void changePassword(User user, String newPassword){
        try {
            user.setPassword(newPassword);
            userRepository.save(user);
        }catch (Exception e){
            LOGGER.error(e.toString());
            throw new DatabaseException();
        }
    }

    @Value("${exception.user.notfound}")
    private String userNotFound;

    @Value("${exception.user.socialaccount}")
    private String userSocialAccount;
}
