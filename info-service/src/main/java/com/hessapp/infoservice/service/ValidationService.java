package com.hessapp.infoservice.service;

import com.hessapp.infoservice.client.spend.dto.CreateSpendRequestDTO;
import com.hessapp.infoservice.dto.GroupStatusRequestDTO;
import org.springframework.stereotype.Service;

@Service
public class ValidationService {

    public boolean isValidCreateSpendRequest(CreateSpendRequestDTO createSpendRequestDTO){
        boolean isValid = createSpendRequestDTO.getGroupId()>0 && createSpendRequestDTO.getDescription()!=null
                && !(createSpendRequestDTO.getDescription().equals("")) && createSpendRequestDTO.getFrom()!=null
                && !(createSpendRequestDTO.getFrom().equals("")) && createSpendRequestDTO.getTotalAmount()>0;

        return isValid;
    }

    public boolean isValidGroupStatusReq(GroupStatusRequestDTO groupStatusRequestDTO){
        boolean isValid = groupStatusRequestDTO.getGroupId()>0 && groupStatusRequestDTO.getNickname()!=null
                && !(groupStatusRequestDTO.getNickname().equals(""));

        return isValid;
    }

}
