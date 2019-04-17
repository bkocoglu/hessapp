package com.hassapp.api.client.message;

import com.hassapp.api.client.message.dto.MessagesRequest;
import com.hassapp.api.client.message.dto.MessagesResponse;
import com.hassapp.api.dto.Group;
import com.hassapp.api.providers.URLProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Component
public class MessageClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageClient.class);
    private String baseUrl = URLProvider.apiGateway;

    private RestTemplate restTemplate;

    MessageClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public MessagesResponse getMessages(String nickname, List<Group> groups) {
        try {
            String subUrl = "/chat-service/messages/user/groups";
            URI uri = new URI(baseUrl + subUrl);

            List<String> groupIds = this.convertGroupId(groups);

            List<Integer> deneme = new ArrayList<>();

            MessagesRequest messagesRequest = new MessagesRequest(nickname,deneme);

            ResponseEntity<MessagesResponse> res = restTemplate.postForEntity(uri, messagesRequest, MessagesResponse.class);
            LOGGER.info(res.toString());
            if (res.getStatusCode().isError()) {
                return null;
            }

            return res.getBody();
        } catch (Exception e) {
            LOGGER.error(e.toString());
            return null;
        }
    }

    private List<String> convertGroupId(List<Group> groups) {
        List<String> ids = new ArrayList<>();

        for (Group group: groups) {
            ids.add(group.getGroupID());
        }

        return ids;
    }
}
