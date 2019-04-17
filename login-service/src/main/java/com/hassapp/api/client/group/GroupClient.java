package com.hassapp.api.client.group;

import com.hassapp.api.client.group.dto.Group;
import com.hassapp.api.client.group.dto.LoginUserResponse;
import com.hassapp.api.exceptions.ClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Component
@PropertySource("classpath:message.properties")
public class GroupClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupClient.class);

    private RestTemplate restTemplate;

    GroupClient(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public List<Group> getGroupList(String nickname){

        try {
            String url = baseUrl + subUrl + nickname;

            URI uri = new URI(url);

            ResponseEntity<LoginUserResponse> res = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    null,
                    LoginUserResponse.class);

            if (res.getStatusCode().isError())
                throw new ClientException(res.toString());

            return res.getBody().getGroups();

        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new ClientException(serviceError);
        }
    }

    @Value("${url.base}")
    private String baseUrl;

    @Value("${url.group.login}")
    private String subUrl;

    @Value("${exception.service}")
    private String serviceError;
}
