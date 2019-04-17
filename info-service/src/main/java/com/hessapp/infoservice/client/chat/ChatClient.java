package com.hessapp.infoservice.client.chat;

import com.hessapp.infoservice.client.spend.dto.CreateSpendResponseDTO;
import com.hessapp.infoservice.dto.CreateGroupResponseDTO;
import com.hessapp.infoservice.dto.GeneralResponseDTO;
import com.hessapp.infoservice.providers.URLProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
public class ChatClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatClient.class);
    private final String baseURL = URLProvider.apiGateway;

    private RestTemplate restTemplate;

    ChatClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void deleteGroupMessages(int groupId) {
        try {
            String subUrl = "/chat-service/group/" + groupId + "/delete";
            String url = this.baseURL + subUrl;

            URI uri = new URI(url);

            restTemplate.delete(uri);
        }catch (Exception e) {
            LOGGER.error(e.toString());
        }
    }

    public void createGroupMessage(CreateGroupResponseDTO createGroupResponseDTO) {
        try {
            String subUrl = "/chat-service/group/create";
            String url = this.baseURL + subUrl;
            URI uri = new URI(url);

            restTemplate.postForEntity(uri, createGroupResponseDTO, GeneralResponseDTO.class);

        }catch (Exception e) {
            LOGGER.error(e.toString());
        }
    }

    public void createSpendMessage(CreateSpendResponseDTO createSpendResponseDTO) {
        try {
            String subUrl = "/chat-service/spend/create";
            String url = this.baseURL + subUrl;
            URI uri = new URI(url);

            restTemplate.postForEntity(uri, createSpendResponseDTO, GeneralResponseDTO.class);
        }catch (Exception e) {
            LOGGER.info(e.getMessage());
        }
    }
}
