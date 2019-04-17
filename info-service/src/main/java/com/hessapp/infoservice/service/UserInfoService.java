package com.hessapp.infoservice.service;

import com.hessapp.infoservice.dto.UserNamesDTO;
import com.hessapp.infoservice.providers.URLProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@Service
public class UserInfoService {
    static final Logger LOGGER = LoggerFactory.getLogger(UserInfoService.class);

    final String baseUrl = URLProvider.apiGateway;

    private RestTemplate restTemplate;

    UserInfoService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public List<String> getUserNames(List<String> nicknames){
        try {
            String subUrl = "/login-service/user/namesInfo";

            URI uri = new URI(baseUrl + subUrl);

            UserNamesDTO userNamesDTO = new UserNamesDTO(nicknames);

            ResponseEntity<UserNamesDTO> res = restTemplate.postForEntity(uri , userNamesDTO , UserNamesDTO.class);

            if (res.getStatusCode().isError()){
                return null;
            }

            return res.getBody().getNicnames();
        }catch (Exception e){
            LOGGER.error(e.toString());
            return null;
        }


    }
}
