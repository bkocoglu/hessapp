package com.hessapp.infoservice.client.group;

import com.hessapp.infoservice.client.group.dto.Group;
import com.hessapp.infoservice.dto.CreateGroupRequestDTO;
import com.hessapp.infoservice.dto.DeleteGroupRequestDTO;
import com.hessapp.infoservice.dto.DeleteGroupResponseDTO;
import com.hessapp.infoservice.providers.URLProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Component
public class GroupClient {
    static final Logger LOGGER = LoggerFactory.getLogger(GroupClient.class);

    private RestTemplate restTemplate;

    GroupClient(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public Group createGroup(CreateGroupRequestDTO createGroupRequestDTO){

        try {
            String url = URLProvider.apiGroupCreate;

            URI uri = new URI(url);

            ResponseEntity<Group> res = restTemplate.postForEntity(uri, createGroupRequestDTO, Group.class);

            if (res.getStatusCode().isError()){
                return null;
            }

            return res.getBody();
        } catch (URISyntaxException e) {
            LOGGER.error(e.toString());
            return null;
        }

    }

    public DeleteGroupResponseDTO deleteGroup(DeleteGroupRequestDTO deleteGroupRequestDTO) {
        try {
            //LOGGER.info(deleteGroupRequestDTO.toString());
            String url = URLProvider.apiGroupDelete;

            URI uri = new URI(url);

            ResponseEntity<DeleteGroupResponseDTO> res =
                    restTemplate.postForEntity(uri, deleteGroupRequestDTO, DeleteGroupResponseDTO.class);

            if (res.getStatusCode().isError()){
             //   LOGGER.error("60: " + res.toString());
                return null;
            }

            return res.getBody();

        }catch (Exception e){
            LOGGER.error(e.toString());
            return null;
        }
    }
}
