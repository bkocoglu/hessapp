package com.hessapp.infoservice.client.spend;

import com.hessapp.infoservice.client.spend.dto.*;
import com.hessapp.infoservice.dto.GroupStatusRequestDTO;
import com.hessapp.infoservice.dto.GroupStatusResponseDTO;
import com.hessapp.infoservice.providers.URLProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
public class SpendClient {
    static final Logger LOGGER = LoggerFactory.getLogger(SpendClient.class);

    RestTemplate restTemplate;

    SpendClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public CreateSpendResponseDTO clientCreateSpend(CreateSpendRequestDTO createSpendRequestDTO){
        try {
            String url = URLProvider.apiSpendCreate;

            URI uri = new URI(url);

            ResponseEntity<CreateSpendResponseDTO> res =
                    restTemplate.postForEntity(uri, createSpendRequestDTO, CreateSpendResponseDTO.class);

            if (res.getStatusCode().isError()){
                LOGGER.error("Create Spend Response Code => " + res.getStatusCodeValue());
                return null;
            }

            return res.getBody();

        }catch (Exception e) {
            LOGGER.error(e.toString());
            return null;
        }
    }

    public ListSpendsResponseDTO clientListSpends(int groupId){
        try {
            String url = URLProvider.apiGetSpends;

            url += "?id=" + groupId;

            URI uri = new URI(url);

            ResponseEntity<ListSpendsResponseDTO> res =
                    restTemplate.getForEntity(uri, ListSpendsResponseDTO.class);

            if (res.getStatusCode().isError()){
                LOGGER.error("Create Spend Response Code => " + res.getStatusCodeValue());
                return null;
            }

            return res.getBody();

        }catch (Exception e) {
            LOGGER.error(e.toString());
            return null;
        }
    }

    public GeneralStatusListDTO clientGeneralStatus(String nickname){
        try {
            String url = URLProvider.apiSpendGeneralStatus;

            url += ("?id=" + nickname);

            URI uri = new URI(url);

            ResponseEntity<GeneralStatusListDTO> res =
                    restTemplate.getForEntity(uri, GeneralStatusListDTO.class);

            if ( res.getStatusCode().isError() ){
                LOGGER.error("General Status List HTTP Code => " + res.getStatusCodeValue());
                return null;
            }

            return res.getBody();
        }catch (Exception e) {
            LOGGER.error(e.toString());
            return null;
        }
    }

    public GroupStatusResponseDTO clientGroupStatus(GroupStatusRequestDTO groupStatusRequestDTO) {
        try {
            String url = URLProvider.apiSpendGroupStatus;
            URI uri = new URI(url);

            ResponseEntity<GroupStatusResponseDTO> res =
                    restTemplate.postForEntity(uri, groupStatusRequestDTO, GroupStatusResponseDTO.class);

            if ( res.getStatusCode().isError() ){
                LOGGER.error("Group Status HTTP Code => " + res.getStatusCodeValue());
                return null;
            }

            return res.getBody();

        }catch (Exception e) {
            LOGGER.error(e.toString());
            return null;
        }
    }

    public PayDebtResponseDTO clientPayDebt(int activityId){
        try {
            String url = URLProvider.apiSpendPayDebt;

            url += ("?id=" + activityId);

            URI uri = new URI(url);

            ResponseEntity<PayDebtResponseDTO> res =
                    restTemplate.getForEntity(uri, PayDebtResponseDTO.class);

            if ( res.getStatusCode().isError() ){
                LOGGER.error("Pay Debt HTTP Code => " + res.getStatusCodeValue());
                return null;
            }

            return res.getBody();

        }catch (Exception e) {
            LOGGER.error(e.toString());
            return null;
        }
    }
}
