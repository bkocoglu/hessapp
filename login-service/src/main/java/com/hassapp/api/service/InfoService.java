package com.hassapp.api.service;

import com.hassapp.api.client.group.GroupClient;
import com.hassapp.api.client.group.dto.GroupListResponseDTO;
import com.hassapp.api.exceptions.DatabaseException;
import com.hassapp.api.exceptions.UserNotFoundException;
import com.hassapp.api.model.City;
import com.hassapp.api.model.User;
import com.hassapp.api.repository.CityRepository;
import com.hassapp.api.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@PropertySource("classpath:message.properties")
public class InfoService {
    static final Logger LOGGER = LoggerFactory.getLogger(InfoService.class);

    private UserRepository userRepository;
    private GroupClient groupClient;
    private CityRepository cityRepository;

    InfoService(UserRepository userRepository,
                GroupClient groupClient,
                CityRepository cityRepository){
        this.userRepository = userRepository;
        this.groupClient = groupClient;
        this.cityRepository = cityRepository;
    }

    public List<String> nicknamesConvertNames(List<String> nicknames){
        try {

            List<String> names = new ArrayList<String>();
            for (String item:nicknames) {
                Optional<User> user = userRepository.findById(item);
                names.add(user.get().getName());
            }

            return names;
        }catch (Exception e){
            LOGGER.error(e.toString());
            return null;
        }

    }

    public User userInformation(String nicname){
        try {
            Optional<User> userOpt = userRepository.findById(nicname);

            if (userOpt == null){
                throw new UserNotFoundException(nicknameFound);
            }
            return userOpt.get();
        }catch (Exception e){
            LOGGER.error(e.toString());
            throw new DatabaseException();
    }
    }

    public List<City> getAllCity() throws DatabaseException {
        try {
            List<City> cities = cityRepository.findAll();
            if (cities.isEmpty()){
                throw new DatabaseException();
            }
            return cities;
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            throw new DatabaseException();
        }
    }

    public User save(User user){
        return userRepository.save(user);
    }

    @Value("${exception.nickname}")
    private String nicknameFound;
}
